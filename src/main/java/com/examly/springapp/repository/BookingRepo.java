package com.examly.springapp.repository;

import com.examly.springapp.model.Booking;
import com.examly.springapp.model.Guest;
import com.examly.springapp.model.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepo extends JpaRepository<Booking, Long> {
    
    List<Booking> findByGuest(Guest guest);
    
    List<Booking> findByRoom(Room room);
    
    List<Booking> findByStatus(Booking.BookingStatus status);
    
    @Query("SELECT b FROM Booking b WHERE b.checkInDate >= :startDate AND b.checkOutDate <= :endDate")
    List<Booking> findBookingsByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    @Query("SELECT b FROM Booking b WHERE b.room = :room AND " +
           "((b.checkInDate <= :checkOut AND b.checkOutDate >= :checkIn) AND b.status != 'CANCELLED')")
    List<Booking> findConflictingBookings(@Param("room") Room room, 
                                        @Param("checkIn") LocalDate checkIn, 
                                        @Param("checkOut") LocalDate checkOut);
    
    @Query("SELECT b FROM Booking b WHERE b.guest.guestId = :guestId")
    List<Booking> findByGuestId(@Param("guestId") Long guestId);
    
    @Query("SELECT b FROM Booking b WHERE b.room.roomId = :roomId")
    List<Booking> findByRoomId(@Param("roomId") Long roomId);
    
    Page<Booking> findByStatus(Booking.BookingStatus status, Pageable pageable);
    
    @Query("SELECT COUNT(b) FROM Booking b WHERE b.status = :status")
    long countByStatus(@Param("status") Booking.BookingStatus status);
}