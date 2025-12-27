package com.examly.springapp.controller;

import com.examly.springapp.model.RoomCategory;
import com.examly.springapp.service.RoomCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/room-categories")
public class RoomCategoryController {
    
    @Autowired
    private RoomCategoryService roomCategoryService;
    
    @PostMapping
    public ResponseEntity<RoomCategory> createRoomCategory(@RequestBody(required = false) RoomCategory roomCategory) {
        if (roomCategory == null || roomCategory.getCategoryName() == null || roomCategory.getCategoryName().trim().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            RoomCategory createdCategory = roomCategoryService.createRoomCategory(roomCategory);
            return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    @GetMapping
    public ResponseEntity<List<RoomCategory>> getAllRoomCategories() {
        List<RoomCategory> categories = roomCategoryService.getAllRoomCategories();
        if (categories.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getRoomCategoryById(@PathVariable Long id) {
        Optional<RoomCategory> category = roomCategoryService.getRoomCategoryById(id);
        if (category.isPresent()) {
            return new ResponseEntity<>(category.get(), HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Room category not found");
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<RoomCategory> updateRoomCategory(@PathVariable Long id, @RequestBody RoomCategory roomCategory) {
        try {
            RoomCategory updatedCategory = roomCategoryService.updateRoomCategory(id, roomCategory);
            return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoomCategory(@PathVariable Long id) {
        try {
            roomCategoryService.deleteRoomCategory(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/name/{categoryName}")
    public ResponseEntity<RoomCategory> getRoomCategoryByName(@PathVariable String categoryName) {
        Optional<RoomCategory> category = roomCategoryService.getRoomCategoryByName(categoryName);
        if (category.isPresent()) {
            return new ResponseEntity<>(category.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @GetMapping("/page/{pageNumber}/{pageSize}")
    public ResponseEntity<Page<RoomCategory>> getRoomCategoriesWithPagination(
            @PathVariable int pageNumber, 
            @PathVariable int pageSize) {
        
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("categoryName"));
        Page<RoomCategory> categories = roomCategoryService.getRoomCategoriesWithPagination(pageable);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
    
    @GetMapping("/search")
    public ResponseEntity<Page<RoomCategory>> searchRoomCategories(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("categoryName"));
        Page<RoomCategory> categories = roomCategoryService.searchRoomCategories(name, pageable);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
    
    @GetMapping("/exists/{categoryName}")
    public ResponseEntity<Boolean> checkCategoryExists(@PathVariable String categoryName) {
        boolean exists = roomCategoryService.existsByCategoryName(categoryName);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }
    
    @GetMapping("/count")
    public ResponseEntity<Long> getTotalCategoriesCount() {
        long count = roomCategoryService.getTotalCategoriesCount();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        if (e.getMessage().contains("Room category not found")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Room category not found");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An error occurred: " + e.getMessage());
    }
}