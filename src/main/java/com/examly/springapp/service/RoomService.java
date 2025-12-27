package com.examly.springapp.service;

import com.examly.springapp.model.Room;
import com.examly.springapp.model.RoomCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface RoomService {
    
    Room createRoom(Room room);
    
    List<Room> getAllRooms();
    
    Optional<Room> getRoomById(Long roomId);
    
    Room updateRoom(Long roomId, Room room);
    
    void deleteRoom(Long roomId);
    
    List<Room> getRoomsByNumber(String roomNumber);
    
    List<Room> getAvailableRooms();
    
    List<Room> getRoomsByCategory(RoomCategory roomCategory);
    
    List<Room> getRoomsByCategoryName(String categoryName);
    
    List<Room> getRoomsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);
    
    List<Room> getAvailableRoomsByCategory(String categoryName);
    
    Room updateRoomAvailability(Long roomId, Boolean available);
    
    Room updateRoomPrice(Long roomId, BigDecimal newPrice);
    
    Room applySeasonalRate(Long roomId, BigDecimal seasonalRate);
    
    Room applyDynamicPricing(Long roomId, BigDecimal multiplier);
    
    Page<Room> getRoomsWithPagination(Pageable pageable);
    
    Page<Room> getAvailableRoomsWithPagination(Pageable pageable);
    
    boolean existsByRoomNumber(String roomNumber);
}