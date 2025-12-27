package com.examly.springapp.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "rooms")
public class Room {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;
    
    @Column(nullable = false, unique = true)
    private String roomNumber;
    
    @Column(nullable = false)
    private BigDecimal pricePerNight;
    
    @Column(nullable = false)
    private Boolean available = true;
    
    @Column
    private BigDecimal seasonalRate;
    
    @Column
    private BigDecimal dynamicPriceMultiplier = BigDecimal.ONE;
    
    @Column
    private LocalDateTime lastPriceUpdate;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    private RoomCategory roomCategory;
    
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Booking> bookings;
    
    public Room() {}
    
    public Room(String roomNumber, BigDecimal pricePerNight, Boolean available, RoomCategory roomCategory) {
        this.roomNumber = roomNumber;
        this.pricePerNight = pricePerNight;
        this.available = available;
        this.roomCategory = roomCategory;
        this.lastPriceUpdate = LocalDateTime.now();
    }
    
    public Long getRoomId() {
        return roomId;
    }
    
    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }
    
    public String getRoomNumber() {
        return roomNumber;
    }
    
    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }
    
    public BigDecimal getPricePerNight() {
        return pricePerNight;
    }
    
    public void setPricePerNight(BigDecimal pricePerNight) {
        this.pricePerNight = pricePerNight;
        this.lastPriceUpdate = LocalDateTime.now();
    }
    
    public Boolean getAvailable() {
        return available;
    }
    
    public void setAvailable(Boolean available) {
        this.available = available;
    }
    
    public BigDecimal getSeasonalRate() {
        return seasonalRate;
    }
    
    public void setSeasonalRate(BigDecimal seasonalRate) {
        this.seasonalRate = seasonalRate;
    }
    
    public BigDecimal getDynamicPriceMultiplier() {
        return dynamicPriceMultiplier;
    }
    
    public void setDynamicPriceMultiplier(BigDecimal dynamicPriceMultiplier) {
        this.dynamicPriceMultiplier = dynamicPriceMultiplier;
    }
    
    public LocalDateTime getLastPriceUpdate() {
        return lastPriceUpdate;
    }
    
    public void setLastPriceUpdate(LocalDateTime lastPriceUpdate) {
        this.lastPriceUpdate = lastPriceUpdate;
    }
    
    public RoomCategory getRoomCategory() {
        return roomCategory;
    }
    
    public void setRoomCategory(RoomCategory roomCategory) {
        this.roomCategory = roomCategory;
    }
    
    public List<Booking> getBookings() {
        return bookings;
    }
    
    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
    
    public BigDecimal getCurrentPrice() {
        BigDecimal currentPrice = pricePerNight;
        if (seasonalRate != null) {
            currentPrice = currentPrice.add(seasonalRate);
        }
        if (dynamicPriceMultiplier != null) {
            currentPrice = currentPrice.multiply(dynamicPriceMultiplier);
        }
        return currentPrice;
    }
}