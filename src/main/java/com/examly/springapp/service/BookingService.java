package com.examly.springapp.service;

import com.examly.springapp.model.Booking;
import com.examly.springapp.model.Guest;
import com.examly.springapp.model.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookingService {
    
    Booking createBooking(Booking booking);
    
    List<Booking> getAllBookings();
    
    Optional<Booking> getBookingById(Long bookingId);
    
    Booking updateBooking(Long bookingId, Booking booking);
    
    void deleteBooking(Long bookingId);
    
    List<Booking> getBookingsByGuest(Guest guest);
    
    List<Booking> getBookingsByRoom(Room room);
    
    List<Booking> getBookingsByStatus(Booking.BookingStatus status);
    
    List<Booking> getBookingsByDateRange(LocalDate startDate, LocalDate endDate);
    
    List<Booking> getBookingsByGuestId(Long guestId);
    
    List<Booking> getBookingsByRoomId(Long roomId);
    
    Booking updateBookingStatus(Long bookingId, Booking.BookingStatus status);
    
    boolean isRoomAvailable(Room room, LocalDate checkIn, LocalDate checkOut);
    
    List<Booking> getConflictingBookings(Room room, LocalDate checkIn, LocalDate checkOut);
    
    Page<Booking> getBookingsWithPagination(Pageable pageable);
    
    Page<Booking> getBookingsByStatus(Booking.BookingStatus status, Pageable pageable);
    
    long getBookingsCountByStatus(Booking.BookingStatus status);
}