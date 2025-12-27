package com.examly.springapp.service;

import com.examly.springapp.model.RoomCategory;
import com.examly.springapp.repository.RoomCategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RoomCategoryServiceImpl implements RoomCategoryService {
    
    @Autowired
    private RoomCategoryRepo roomCategoryRepo;
    
    @Override
    public RoomCategory createRoomCategory(RoomCategory roomCategory) {
        if (roomCategoryRepo.existsByCategoryName(roomCategory.getCategoryName())) {
            throw new RuntimeException("Room category with name '" + roomCategory.getCategoryName() + "' already exists");
        }
        return roomCategoryRepo.save(roomCategory);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<RoomCategory> getAllRoomCategories() {
        return roomCategoryRepo.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<RoomCategory> getRoomCategoryById(Long categoryId) {
        return roomCategoryRepo.findById(categoryId);
    }
    
    @Override
    public RoomCategory updateRoomCategory(Long categoryId, RoomCategory roomCategory) {
        Optional<RoomCategory> existingCategory = roomCategoryRepo.findById(categoryId);
        if (existingCategory.isPresent()) {
            RoomCategory category = existingCategory.get();
            
            // Check if the new name conflicts with existing categories (excluding current one)
            if (!category.getCategoryName().equals(roomCategory.getCategoryName()) && 
                roomCategoryRepo.existsByCategoryName(roomCategory.getCategoryName())) {
                throw new RuntimeException("Room category with name '" + roomCategory.getCategoryName() + "' already exists");
            }
            
            category.setCategoryName(roomCategory.getCategoryName());
            category.setDescription(roomCategory.getDescription());
            return roomCategoryRepo.save(category);
        }
        throw new RuntimeException("Room category not found with id: " + categoryId);
    }
    
    @Override
    public void deleteRoomCategory(Long categoryId) {
        if (!roomCategoryRepo.existsById(categoryId)) {
            throw new RuntimeException("Room category not found with id: " + categoryId);
        }
        roomCategoryRepo.deleteById(categoryId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<RoomCategory> getRoomCategoryByName(String categoryName) {
        return roomCategoryRepo.findByCategoryName(categoryName);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<RoomCategory> getRoomCategoriesWithPagination(Pageable pageable) {
        return roomCategoryRepo.findAll(pageable);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<RoomCategory> searchRoomCategories(String name, Pageable pageable) {
        return roomCategoryRepo.findByCategoryNameContaining(name, pageable);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existsByCategoryName(String categoryName) {
        return roomCategoryRepo.existsByCategoryName(categoryName);
    }
    
    @Override
    @Transactional(readOnly = true)
    public long getTotalCategoriesCount() {
        return roomCategoryRepo.countAllCategories();
    }
}