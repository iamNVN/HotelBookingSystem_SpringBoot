package com.examly.springapp.repository;

import com.examly.springapp.model.Room;
import com.examly.springapp.model.RoomCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepo extends JpaRepository<Room, Long> {
    
    Optional<Room> findByRoomNumber(String roomNumber);
    
    List<Room> findByRoomNumberContaining(String roomNumber);
    
    List<Room> findByAvailable(Boolean available);
    
    List<Room> findByRoomCategory(RoomCategory roomCategory);
    
    @Query("SELECT r FROM Room r WHERE r.roomCategory.categoryName = :categoryName")
    List<Room> findByRoomCategoryCategoryName(@Param("categoryName") String categoryName);
    
    @Query("SELECT r FROM Room r WHERE r.pricePerNight BETWEEN :minPrice AND :maxPrice")
    List<Room> findByPriceRange(@Param("minPrice") BigDecimal minPrice, @Param("maxPrice") BigDecimal maxPrice);
    
    @Query("SELECT r FROM Room r WHERE r.available = true AND r.roomCategory.categoryName = :categoryName")
    List<Room> findAvailableRoomsByCategory(@Param("categoryName") String categoryName);
    
    boolean existsByRoomNumber(String roomNumber);
    
    Page<Room> findByAvailable(Boolean available, Pageable pageable);
}