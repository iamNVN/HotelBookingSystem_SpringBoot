package com.examly.springapp.repository;

import com.examly.springapp.model.Guest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GuestRepo extends JpaRepository<Guest, Long> {
    
    Optional<Guest> findByEmail(String email);
    
    List<Guest> findByEmailContaining(String email);
    
    Optional<Guest> findByPhone(String phone);
    
    List<Guest> findByPhoneContaining(String phone);
    
    List<Guest> findByNameContainingIgnoreCase(String name);
    
    boolean existsByEmail(String email);
    
    boolean existsByPhone(String phone);
    
    @Query("SELECT g FROM Guest g WHERE g.name LIKE %:name% OR g.email LIKE %:email%")
    Page<Guest> findByNameOrEmail(@Param("name") String name, @Param("email") String email, Pageable pageable);
    
    @Query("SELECT COUNT(g) FROM Guest g")
    long countAllGuests();
}