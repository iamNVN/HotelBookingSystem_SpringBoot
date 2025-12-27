package com.examly.springapp.service;

import com.examly.springapp.model.Guest;
import com.examly.springapp.repository.GuestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GuestServiceImpl implements GuestService {
    
    @Autowired
    private GuestRepo guestRepo;
    
    @Override
    public Guest createGuest(Guest guest) {
        if (guestRepo.existsByEmail(guest.getEmail())) {
            throw new RuntimeException("Guest with email '" + guest.getEmail() + "' already exists");
        }
        if (guestRepo.existsByPhone(guest.getPhone())) {
            throw new RuntimeException("Guest with phone '" + guest.getPhone() + "' already exists");
        }
        guest.setRegistrationDate(LocalDateTime.now());
        return guestRepo.save(guest);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Guest> getAllGuests() {
        return guestRepo.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Guest> getGuestById(Long guestId) {
        return guestRepo.findById(guestId);
    }
    
    @Override
    public Guest updateGuest(Long guestId, Guest guest) {
        Optional<Guest> existingGuest = guestRepo.findById(guestId);
        if (existingGuest.isPresent()) {
            Guest guestToUpdate = existingGuest.get();
            
            // Check if the new email conflicts with existing guests (excluding current one)
            if (!guestToUpdate.getEmail().equals(guest.getEmail()) && 
                guestRepo.existsByEmail(guest.getEmail())) {
                throw new RuntimeException("Guest with email '" + guest.getEmail() + "' already exists");
            }
            
            // Check if the new phone conflicts with existing guests (excluding current one)
            if (!guestToUpdate.getPhone().equals(guest.getPhone()) && 
                guestRepo.existsByPhone(guest.getPhone())) {
                throw new RuntimeException("Guest with phone '" + guest.getPhone() + "' already exists");
            }
            
            guestToUpdate.setName(guest.getName());
            guestToUpdate.setPhone(guest.getPhone());
            guestToUpdate.setEmail(guest.getEmail());
            guestToUpdate.setAddress(guest.getAddress());
            
            return guestRepo.save(guestToUpdate);
        }
        throw new RuntimeException("Guest not found with id: " + guestId);
    }
    
    @Override
    public void deleteGuest(Long guestId) {
        if (!guestRepo.existsById(guestId)) {
            throw new RuntimeException("Guest not found with id: " + guestId);
        }
        guestRepo.deleteById(guestId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Guest> getGuestsByEmail(String email) {
        Optional<Guest> guest = guestRepo.findByEmail(email);
        return guest.map(List::of).orElse(List.of());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Guest> getGuestsByPhone(String phone) {
        Optional<Guest> guest = guestRepo.findByPhone(phone);
        return guest.map(List::of).orElse(List.of());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Guest> searchGuestsByName(String name) {
        return guestRepo.findByNameContainingIgnoreCase(name);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<Guest> getGuestsWithPagination(Pageable pageable) {
        return guestRepo.findAll(pageable);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<Guest> searchGuests(String name, String email, Pageable pageable) {
        return guestRepo.findByNameOrEmail(name, email, pageable);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return guestRepo.existsByEmail(email);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existsByPhone(String phone) {
        return guestRepo.existsByPhone(phone);
    }
    
    @Override
    @Transactional(readOnly = true)
    public long getTotalGuestsCount() {
        return guestRepo.countAllGuests();
    }
}