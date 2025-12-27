package com.examly.springapp.service;

import com.examly.springapp.model.Booking;
import com.examly.springapp.model.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PaymentService {
    
    Payment createPayment(Payment payment);
    
    List<Payment> getAllPayments();
    
    Optional<Payment> getPaymentById(Long paymentId);
    
    Payment updatePayment(Long paymentId, Payment payment);
    
    void deletePayment(Long paymentId);
    
    List<Payment> getPaymentsByBooking(Booking booking);
    
    List<Payment> getPaymentsByStatus(Payment.PaymentStatus status);
    
    List<Payment> getPaymentsByMethod(Payment.PaymentMethod paymentMethod);
    
    List<Payment> getPaymentsByDateRange(LocalDateTime startDate, LocalDateTime endDate);
    
    List<Payment> getPaymentsByBookingId(Long bookingId);
    
    Payment updatePaymentStatus(Long paymentId, Payment.PaymentStatus status);
    
    Payment processPayment(Long paymentId);
    
    Payment refundPayment(Long paymentId);
    
    BigDecimal getTotalCompletedPayments();
    
    BigDecimal getTotalPaidAmountForBooking(Booking booking);
    
    Page<Payment> getPaymentsWithPagination(Pageable pageable);
    
    Page<Payment> getPaymentsByStatus(Payment.PaymentStatus status, Pageable pageable);
    
    long getPaymentsCountByStatus(Payment.PaymentStatus status);
    
    Optional<Payment> getPaymentByTransactionId(String transactionId);
}