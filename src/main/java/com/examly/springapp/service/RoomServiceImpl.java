package com.examly.springapp.service;

import com.examly.springapp.model.Room;
import com.examly.springapp.model.RoomCategory;
import com.examly.springapp.repository.RoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RoomServiceImpl implements RoomService {
    
    @Autowired
    private RoomRepo roomRepo;
    
    @Override
    public Room createRoom(Room room) {
        if (roomRepo.existsByRoomNumber(room.getRoomNumber())) {
            throw new RuntimeException("Room with number '" + room.getRoomNumber() + "' already exists");
        }
        room.setLastPriceUpdate(LocalDateTime.now());
        return roomRepo.save(room);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Room> getAllRooms() {
        return roomRepo.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Room> getRoomById(Long roomId) {
        return roomRepo.findById(roomId);
    }
    
    @Override
    public Room updateRoom(Long roomId, Room room) {
        Optional<Room> existingRoom = roomRepo.findById(roomId);
        if (existingRoom.isPresent()) {
            Room roomToUpdate = existingRoom.get();
            
            // Check if the new room number conflicts with existing rooms (excluding current one)
            if (!roomToUpdate.getRoomNumber().equals(room.getRoomNumber()) && 
                roomRepo.existsByRoomNumber(room.getRoomNumber())) {
                throw new RuntimeException("Room with number '" + room.getRoomNumber() + "' already exists");
            }
            
            roomToUpdate.setRoomNumber(room.getRoomNumber());
            roomToUpdate.setPricePerNight(room.getPricePerNight());
            roomToUpdate.setAvailable(room.getAvailable());
            roomToUpdate.setRoomCategory(room.getRoomCategory());
            roomToUpdate.setSeasonalRate(room.getSeasonalRate());
            roomToUpdate.setDynamicPriceMultiplier(room.getDynamicPriceMultiplier());
            roomToUpdate.setLastPriceUpdate(LocalDateTime.now());
            
            return roomRepo.save(roomToUpdate);
        }
        throw new RuntimeException("Room not found with id: " + roomId);
    }
    
    @Override
    public void deleteRoom(Long roomId) {
        if (!roomRepo.existsById(roomId)) {
            throw new RuntimeException("Room not found with id: " + roomId);
        }
        roomRepo.deleteById(roomId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Room> getRoomsByNumber(String roomNumber) {
        Optional<Room> room = roomRepo.findByRoomNumber(roomNumber);
        return room.map(List::of).orElse(List.of());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Room> getAvailableRooms() {
        return roomRepo.findByAvailable(true);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Room> getRoomsByCategory(RoomCategory roomCategory) {
        return roomRepo.findByRoomCategory(roomCategory);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Room> getRoomsByCategoryName(String categoryName) {
        return roomRepo.findByRoomCategoryCategoryName(categoryName);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Room> getRoomsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return roomRepo.findByPriceRange(minPrice, maxPrice);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Room> getAvailableRoomsByCategory(String categoryName) {
        return roomRepo.findAvailableRoomsByCategory(categoryName);
    }
    
    @Override
    public Room updateRoomAvailability(Long roomId, Boolean available) {
        Optional<Room> roomOpt = roomRepo.findById(roomId);
        if (roomOpt.isPresent()) {
            Room room = roomOpt.get();
            room.setAvailable(available);
            return roomRepo.save(room);
        }
        throw new RuntimeException("Room not found with id: " + roomId);
    }
    
    @Override
    public Room updateRoomPrice(Long roomId, BigDecimal newPrice) {
        Optional<Room> roomOpt = roomRepo.findById(roomId);
        if (roomOpt.isPresent()) {
            Room room = roomOpt.get();
            room.setPricePerNight(newPrice);
            room.setLastPriceUpdate(LocalDateTime.now());
            return roomRepo.save(room);
        }
        throw new RuntimeException("Room not found with id: " + roomId);
    }
    
    @Override
    public Room applySeasonalRate(Long roomId, BigDecimal seasonalRate) {
        Optional<Room> roomOpt = roomRepo.findById(roomId);
        if (roomOpt.isPresent()) {
            Room room = roomOpt.get();
            room.setSeasonalRate(seasonalRate);
            room.setLastPriceUpdate(LocalDateTime.now());
            return roomRepo.save(room);
        }
        throw new RuntimeException("Room not found with id: " + roomId);
    }
    
    @Override
    public Room applyDynamicPricing(Long roomId, BigDecimal multiplier) {
        Optional<Room> roomOpt = roomRepo.findById(roomId);
        if (roomOpt.isPresent()) {
            Room room = roomOpt.get();
            room.setDynamicPriceMultiplier(multiplier);
            room.setLastPriceUpdate(LocalDateTime.now());
            return roomRepo.save(room);
        }
        throw new RuntimeException("Room not found with id: " + roomId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<Room> getRoomsWithPagination(Pageable pageable) {
        return roomRepo.findAll(pageable);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<Room> getAvailableRoomsWithPagination(Pageable pageable) {
        return roomRepo.findByAvailable(true, pageable);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existsByRoomNumber(String roomNumber) {
        return roomRepo.existsByRoomNumber(roomNumber);
    }
}