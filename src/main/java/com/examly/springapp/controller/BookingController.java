package com.examly.springapp.controller;

import com.examly.springapp.model.Booking;
import com.examly.springapp.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    
    @Autowired
    private BookingService bookingService;
    
    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody Booking booking) {
        try {
            Booking createdBooking = bookingService.createBooking(booking);
            return new ResponseEntity<>(createdBooking, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        if (bookings.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
        Optional<Booking> booking = bookingService.getBookingById(id);
        if (booking.isPresent()) {
            return new ResponseEntity<>(booking.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBooking(@PathVariable Long id, @RequestBody Booking booking) {
        try {
            Booking updatedBooking = bookingService.updateBooking(id, booking);
            return new ResponseEntity<>(updatedBooking, HttpStatus.OK);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            }
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        try {
            bookingService.deleteBooking(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/guest/{guestId}")
    public ResponseEntity<List<Booking>> getBookingsByGuestId(@PathVariable Long guestId) {
        List<Booking> bookings = bookingService.getBookingsByGuestId(guestId);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }
    
    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<Booking>> getBookingsByRoomId(@PathVariable Long roomId) {
        List<Booking> bookings = bookingService.getBookingsByRoomId(roomId);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Booking>> getBookingsByStatus(@PathVariable Booking.BookingStatus status) {
        List<Booking> bookings = bookingService.getBookingsByStatus(status);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }
    
    @GetMapping("/date-range")
    public ResponseEntity<List<Booking>> getBookingsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Booking> bookings = bookingService.getBookingsByDateRange(startDate, endDate);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }
    
    @PatchMapping("/{id}/status")
    public ResponseEntity<Booking> updateBookingStatus(@PathVariable Long id, @RequestParam Booking.BookingStatus status) {
        try {
            Booking updatedBooking = bookingService.updateBookingStatus(id, status);
            return new ResponseEntity<>(updatedBooking, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/page/{pageNumber}/{pageSize}")
    public ResponseEntity<Page<Booking>> getBookingsWithPagination(
            @PathVariable int pageNumber, 
            @PathVariable int pageSize) {
        
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("bookingDate").descending());
        Page<Booking> bookings = bookingService.getBookingsWithPagination(pageable);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }
    
    @GetMapping("/status/{status}/page/{pageNumber}/{pageSize}")
    public ResponseEntity<Page<Booking>> getBookingsByStatusWithPagination(
            @PathVariable Booking.BookingStatus status,
            @PathVariable int pageNumber, 
            @PathVariable int pageSize) {
        
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("bookingDate").descending());
        Page<Booking> bookings = bookingService.getBookingsByStatus(status, pageable);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }
    
    @GetMapping("/count/status/{status}")
    public ResponseEntity<Long> getBookingsCountByStatus(@PathVariable Booking.BookingStatus status) {
        long count = bookingService.getBookingsCountByStatus(status);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
}