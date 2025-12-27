package com.examly.springapp.service;

import com.examly.springapp.model.Booking;
import com.examly.springapp.model.Guest;
import com.examly.springapp.model.Room;
import com.examly.springapp.repository.BookingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookingServiceImpl implements BookingService {
    
    @Autowired
    private BookingRepo bookingRepo;
    
    @Override
    public Booking createBooking(Booking booking) {
        // Validate booking dates
        if (booking.getCheckInDate().isAfter(booking.getCheckOutDate())) {
            throw new RuntimeException("Check-in date cannot be after check-out date");
        }
        
        if (booking.getCheckInDate().isBefore(LocalDate.now())) {
            throw new RuntimeException("Check-in date cannot be in the past");
        }
        
        // Check room availability
        if (!isRoomAvailable(booking.getRoom(), booking.getCheckInDate(), booking.getCheckOutDate())) {
            throw new RuntimeException("Room is not available for the selected dates");
        }
        
        // Calculate total amount based on room's current price and number of nights
        long numberOfNights = ChronoUnit.DAYS.between(booking.getCheckInDate(), booking.getCheckOutDate());
        if (numberOfNights <= 0) {
            numberOfNights = 1; // Minimum one night
        }
        
        booking.setTotalAmount(booking.getRoom().getCurrentPrice().multiply(java.math.BigDecimal.valueOf(numberOfNights)));
        booking.setBookingDate(LocalDateTime.now());
        
        return bookingRepo.save(booking);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Booking> getAllBookings() {
        return bookingRepo.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Booking> getBookingById(Long bookingId) {
        return bookingRepo.findById(bookingId);
    }
    
    @Override
    public Booking updateBooking(Long bookingId, Booking booking) {
        Optional<Booking> existingBooking = bookingRepo.findById(bookingId);
        if (existingBooking.isPresent()) {
            Booking bookingToUpdate = existingBooking.get();
            
            // Validate new dates if they are being changed
            if (!bookingToUpdate.getCheckInDate().equals(booking.getCheckInDate()) || 
                !bookingToUpdate.getCheckOutDate().equals(booking.getCheckOutDate()) ||
                !bookingToUpdate.getRoom().getRoomId().equals(booking.getRoom().getRoomId())) {
                
                if (booking.getCheckInDate().isAfter(booking.getCheckOutDate())) {
                    throw new RuntimeException("Check-in date cannot be after check-out date");
                }
                
                // Check availability for new dates/room (excluding current booking)
                List<Booking> conflictingBookings = getConflictingBookings(booking.getRoom(), 
                    booking.getCheckInDate(), booking.getCheckOutDate());
                conflictingBookings.removeIf(b -> b.getBookingId().equals(bookingId));
                
                if (!conflictingBookings.isEmpty()) {
                    throw new RuntimeException("Room is not available for the selected dates");
                }
            }
            
            bookingToUpdate.setCheckInDate(booking.getCheckInDate());
            bookingToUpdate.setCheckOutDate(booking.getCheckOutDate());
            bookingToUpdate.setRoom(booking.getRoom());
            bookingToUpdate.setGuest(booking.getGuest());
            bookingToUpdate.setNumberOfGuests(booking.getNumberOfGuests());
            bookingToUpdate.setSpecialRequests(booking.getSpecialRequests());
            bookingToUpdate.setStatus(booking.getStatus());
            
            // Recalculate total amount
            long numberOfNights = ChronoUnit.DAYS.between(booking.getCheckInDate(), booking.getCheckOutDate());
            if (numberOfNights <= 0) {
                numberOfNights = 1;
            }
            bookingToUpdate.setTotalAmount(booking.getRoom().getCurrentPrice().multiply(java.math.BigDecimal.valueOf(numberOfNights)));
            
            return bookingRepo.save(bookingToUpdate);
        }
        throw new RuntimeException("Booking not found with id: " + bookingId);
    }
    
    @Override
    public void deleteBooking(Long bookingId) {
        if (!bookingRepo.existsById(bookingId)) {
            throw new RuntimeException("Booking not found with id: " + bookingId);
        }
        bookingRepo.deleteById(bookingId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Booking> getBookingsByGuest(Guest guest) {
        return bookingRepo.findByGuest(guest);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Booking> getBookingsByRoom(Room room) {
        return bookingRepo.findByRoom(room);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Booking> getBookingsByStatus(Booking.BookingStatus status) {
        return bookingRepo.findByStatus(status);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Booking> getBookingsByDateRange(LocalDate startDate, LocalDate endDate) {
        return bookingRepo.findBookingsByDateRange(startDate, endDate);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Booking> getBookingsByGuestId(Long guestId) {
        return bookingRepo.findByGuestId(guestId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Booking> getBookingsByRoomId(Long roomId) {
        return bookingRepo.findByRoomId(roomId);
    }
    
    @Override
    public Booking updateBookingStatus(Long bookingId, Booking.BookingStatus status) {
        Optional<Booking> bookingOpt = bookingRepo.findById(bookingId);
        if (bookingOpt.isPresent()) {
            Booking booking = bookingOpt.get();
            booking.setStatus(status);
            return bookingRepo.save(booking);
        }
        throw new RuntimeException("Booking not found with id: " + bookingId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean isRoomAvailable(Room room, LocalDate checkIn, LocalDate checkOut) {
        List<Booking> conflictingBookings = getConflictingBookings(room, checkIn, checkOut);
        return conflictingBookings.isEmpty();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Booking> getConflictingBookings(Room room, LocalDate checkIn, LocalDate checkOut) {
        return bookingRepo.findConflictingBookings(room, checkIn, checkOut);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<Booking> getBookingsWithPagination(Pageable pageable) {
        return bookingRepo.findAll(pageable);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<Booking> getBookingsByStatus(Booking.BookingStatus status, Pageable pageable) {
        return bookingRepo.findByStatus(status, pageable);
    }
    
    @Override
    @Transactional(readOnly = true)
    public long getBookingsCountByStatus(Booking.BookingStatus status) {
        return bookingRepo.countByStatus(status);
    }
}