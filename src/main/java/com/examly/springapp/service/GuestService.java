package com.examly.springapp.service;

import com.examly.springapp.model.Guest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface GuestService {
    
    Guest createGuest(Guest guest);
    
    List<Guest> getAllGuests();
    
    Optional<Guest> getGuestById(Long guestId);
    
    Guest updateGuest(Long guestId, Guest guest);
    
    void deleteGuest(Long guestId);
    
    List<Guest> getGuestsByEmail(String email);
    
    List<Guest> getGuestsByPhone(String phone);
    
    List<Guest> searchGuestsByName(String name);
    
    Page<Guest> getGuestsWithPagination(Pageable pageable);
    
    Page<Guest> searchGuests(String name, String email, Pageable pageable);
    
    boolean existsByEmail(String email);
    
    boolean existsByPhone(String phone);
    
    long getTotalGuestsCount();
}