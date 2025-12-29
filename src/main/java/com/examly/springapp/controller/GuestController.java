package com.examly.springapp.controller;

import com.examly.springapp.model.Guest;
import com.examly.springapp.service.GuestService;
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
@RequestMapping("/api/guests")
public class GuestController {
    
    @Autowired
    private GuestService guestService;
    
    @PostMapping
    public ResponseEntity<?> createGuest(@RequestBody Guest guest) {
        try {
            Guest createdGuest = guestService.createGuest(guest);
            return new ResponseEntity<>(createdGuest, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
    @GetMapping
    public ResponseEntity<List<Guest>> getAllGuests() {
        List<Guest> guests = guestService.getAllGuests();
        if (guests.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(guests, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Guest> getGuestById(@PathVariable Long id) {
        Optional<Guest> guest = guestService.getGuestById(id);
        if (guest.isPresent()) {
            return new ResponseEntity<>(guest.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Guest> updateGuest(@PathVariable Long id, @RequestBody Guest guest) {
        try {
            Guest updatedGuest = guestService.updateGuest(id, guest);
            return new ResponseEntity<>(updatedGuest, HttpStatus.OK);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGuest(@PathVariable Long id) {
        try {
            guestService.deleteGuest(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/email/{email}")
    public ResponseEntity<?> getGuestsByEmail(@PathVariable String email) {
        List<Guest> guests = guestService.getGuestsByEmail(email);
        if (guests.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("No guest found with email: " + email);
        }
        return new ResponseEntity<>(guests, HttpStatus.OK);
    }
    
    @GetMapping("/phone/{phone}")
    public ResponseEntity<?> getGuestsByPhone(@PathVariable String phone) {
        List<Guest> guests = guestService.getGuestsByPhone(phone);
        if (guests.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body("No guest found with phone: " + phone);
        }
        return new ResponseEntity<>(guests, HttpStatus.OK);
    }
    
    @GetMapping("/search/name/{name}")
    public ResponseEntity<List<Guest>> searchGuestsByName(@PathVariable String name) {
        List<Guest> guests = guestService.searchGuestsByName(name);
        return new ResponseEntity<>(guests, HttpStatus.OK);
    }
    
    @GetMapping("/page/{pageNumber}/{pageSize}")
    public ResponseEntity<Page<Guest>> getGuestsWithPagination(
            @PathVariable int pageNumber, 
            @PathVariable int pageSize) {
        
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("name"));
        Page<Guest> guests = guestService.getGuestsWithPagination(pageable);
        return new ResponseEntity<>(guests, HttpStatus.OK);
    }
    
    @GetMapping("/search")
    public ResponseEntity<Page<Guest>> searchGuests(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
        Page<Guest> guests = guestService.searchGuests(name != null ? name : "", 
                                                      email != null ? email : "", 
                                                      pageable);
        return new ResponseEntity<>(guests, HttpStatus.OK);
    }
    
    @GetMapping("/exists/email/{email}")
    public ResponseEntity<Boolean> checkEmailExists(@PathVariable String email) {
        boolean exists = guestService.existsByEmail(email);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }
    
    @GetMapping("/exists/phone/{phone}")
    public ResponseEntity<Boolean> checkPhoneExists(@PathVariable String phone) {
        boolean exists = guestService.existsByPhone(phone);
        return new ResponseEntity<>(exists, HttpStatus.OK);
    }
    
    @GetMapping("/count")
    public ResponseEntity<Long> getTotalGuestsCount() {
        long count = guestService.getTotalGuestsCount();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
}