package com.examly.springapp.service;

import com.examly.springapp.model.RoomCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface RoomCategoryService {
    
    RoomCategory createRoomCategory(RoomCategory roomCategory);
    
    List<RoomCategory> getAllRoomCategories();
    
    Optional<RoomCategory> getRoomCategoryById(Long categoryId);
    
    RoomCategory updateRoomCategory(Long categoryId, RoomCategory roomCategory);
    
    void deleteRoomCategory(Long categoryId);
    
    Optional<RoomCategory> getRoomCategoryByName(String categoryName);
    
    Page<RoomCategory> getRoomCategoriesWithPagination(Pageable pageable);
    
    Page<RoomCategory> searchRoomCategories(String name, Pageable pageable);
    
    boolean existsByCategoryName(String categoryName);
    
    long getTotalCategoriesCount();
}