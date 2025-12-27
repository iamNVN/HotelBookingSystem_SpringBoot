package com.examly.springapp.controller;

import com.examly.springapp.model.Room;
import com.examly.springapp.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {
    
    @Autowired
    private RoomService roomService;
    
    @PostMapping
    public ResponseEntity<?> createRoom(@RequestBody Room room) {
        try {
            Room createdRoom = roomService.createRoom(room);
            return new ResponseEntity<>(createdRoom, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping
    public ResponseEntity<List<Room>> getAllRooms() {
        List<Room> rooms = roomService.getAllRooms();
        if (rooms.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable Long id) {
        Optional<Room> room = roomService.getRoomById(id);
        if (room.isPresent()) {
            return new ResponseEntity<>(room.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable Long id, @RequestBody Room room) {
        try {
            Room updatedRoom = roomService.updateRoom(id, room);
            return new ResponseEntity<>(updatedRoom, HttpStatus.OK);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        try {
            roomService.deleteRoom(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/number/{roomNumber}")
    public ResponseEntity<?> getRoomsByNumber(@PathVariable String roomNumber) {
        List<Room> rooms = roomService.getRoomsByNumber(roomNumber);
        if (rooms.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No room found with number: " + roomNumber);
        }
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }
    
    @GetMapping("/available")
    public ResponseEntity<List<Room>> getAvailableRooms() {
        List<Room> rooms = roomService.getAvailableRooms();
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }
    
    @GetMapping("/category/{categoryName}")
    public ResponseEntity<List<Room>> getRoomsByCategoryName(@PathVariable String categoryName) {
        List<Room> rooms = roomService.getRoomsByCategoryName(categoryName);
        if (rooms.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }
    
    @GetMapping("/price-range")
    public ResponseEntity<List<Room>> getRoomsByPriceRange(
            @RequestParam BigDecimal minPrice, 
            @RequestParam BigDecimal maxPrice) {
        List<Room> rooms = roomService.getRoomsByPriceRange(minPrice, maxPrice);
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }
    
    @GetMapping("/available/category/{categoryName}")
    public ResponseEntity<List<Room>> getAvailableRoomsByCategory(@PathVariable String categoryName) {
        List<Room> rooms = roomService.getAvailableRoomsByCategory(categoryName);
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }
    
    @PatchMapping("/{id}/availability")
    public ResponseEntity<Room> updateRoomAvailability(@PathVariable Long id, @RequestParam Boolean available) {
        try {
            Room updatedRoom = roomService.updateRoomAvailability(id, available);
            return new ResponseEntity<>(updatedRoom, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PatchMapping("/{id}/price")
    public ResponseEntity<Room> updateRoomPrice(@PathVariable Long id, @RequestParam BigDecimal newPrice) {
        try {
            Room updatedRoom = roomService.updateRoomPrice(id, newPrice);
            return new ResponseEntity<>(updatedRoom, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PatchMapping("/{id}/seasonal-rate")
    public ResponseEntity<Room> applySeasonalRate(@PathVariable Long id, @RequestParam BigDecimal seasonalRate) {
        try {
            Room updatedRoom = roomService.applySeasonalRate(id, seasonalRate);
            return new ResponseEntity<>(updatedRoom, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PatchMapping("/{id}/dynamic-pricing")
    public ResponseEntity<Room> applyDynamicPricing(@PathVariable Long id, @RequestParam BigDecimal multiplier) {
        try {
            Room updatedRoom = roomService.applyDynamicPricing(id, multiplier);
            return new ResponseEntity<>(updatedRoom, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/page/{pageNumber}/{pageSize}")
    public ResponseEntity<Page<Room>> getRoomsWithPagination(
            @PathVariable int pageNumber, 
            @PathVariable int pageSize) {
        
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("roomNumber"));
        Page<Room> rooms = roomService.getRoomsWithPagination(pageable);
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }
    
    @GetMapping("/available/page/{pageNumber}/{pageSize}")
    public ResponseEntity<Page<Room>> getAvailableRoomsWithPagination(
            @PathVariable int pageNumber, 
            @PathVariable int pageSize) {
        
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("roomNumber"));
        Page<Room> rooms = roomService.getAvailableRoomsWithPagination(pageable);
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }
    
    @GetMapping("/exists/{roomNumber}")
    public ResponseEntity<Boolean> checkRoomExists(@PathVariable String roomNumber) {
        boolean exists = roomService.existsByRoomNumber(roomNumber);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }
}