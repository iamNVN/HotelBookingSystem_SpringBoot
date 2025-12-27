package com.examly.springapp.repository;

import com.examly.springapp.model.RoomCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomCategoryRepo extends JpaRepository<RoomCategory, Long> {
    
    Optional<RoomCategory> findByCategoryName(String categoryName);
    
    boolean existsByCategoryName(String categoryName);
    
    @Query("SELECT rc FROM RoomCategory rc WHERE rc.categoryName LIKE %:name%")
    Page<RoomCategory> findByCategoryNameContaining(@Param("name") String name, Pageable pageable);
    
    @Query("SELECT COUNT(rc) FROM RoomCategory rc")
    long countAllCategories();
}