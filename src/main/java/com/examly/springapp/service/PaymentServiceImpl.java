package com.examly.springapp.service;

import com.examly.springapp.model.Booking;
import com.examly.springapp.model.Payment;
import com.examly.springapp.repository.PaymentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {
    
    @Autowired
    private PaymentRepo paymentRepo;
    
    @Override
    public Payment createPayment(Payment payment) {
        // Validate payment amount
        if (payment.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Payment amount must be greater than zero");
        }
        
        // Generate transaction ID if not provided
        if (payment.getTransactionId() == null || payment.getTransactionId().isEmpty()) {
            payment.setTransactionId("TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        }
        
        payment.setPaymentDate(LocalDateTime.now());
        return paymentRepo.save(payment);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Payment> getAllPayments() {
        return paymentRepo.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Payment> getPaymentById(Long paymentId) {
        return paymentRepo.findById(paymentId);
    }
    
    @Override
    public Payment updatePayment(Long paymentId, Payment payment) {
        Optional<Payment> existingPayment = paymentRepo.findById(paymentId);
        if (existingPayment.isPresent()) {
            Payment paymentToUpdate = existingPayment.get();
            
            // Validate payment amount
            if (payment.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
                throw new RuntimeException("Payment amount must be greater than zero");
            }
            
            paymentToUpdate.setAmount(payment.getAmount());
            paymentToUpdate.setPaymentMethod(payment.getPaymentMethod());
            paymentToUpdate.setStatus(payment.getStatus());
            paymentToUpdate.setTransactionId(payment.getTransactionId());
            paymentToUpdate.setPaymentReference(payment.getPaymentReference());
            paymentToUpdate.setNotes(payment.getNotes());
            paymentToUpdate.setBooking(payment.getBooking());
            
            return paymentRepo.save(paymentToUpdate);
        }
        throw new RuntimeException("Payment not found with id: " + paymentId);
    }
    
    @Override
    public void deletePayment(Long paymentId) {
        if (!paymentRepo.existsById(paymentId)) {
            throw new RuntimeException("Payment not found with id: " + paymentId);
        }
        paymentRepo.deleteById(paymentId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Payment> getPaymentsByBooking(Booking booking) {
        return paymentRepo.findByBooking(booking);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Payment> getPaymentsByStatus(Payment.PaymentStatus status) {
        return paymentRepo.findByStatus(status);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Payment> getPaymentsByMethod(Payment.PaymentMethod paymentMethod) {
        return paymentRepo.findByPaymentMethod(paymentMethod);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Payment> getPaymentsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return paymentRepo.findPaymentsByDateRange(startDate, endDate);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Payment> getPaymentsByBookingId(Long bookingId) {
        return paymentRepo.findByBookingId(bookingId);
    }
    
    @Override
    public Payment updatePaymentStatus(Long paymentId, Payment.PaymentStatus status) {
        Optional<Payment> paymentOpt = paymentRepo.findById(paymentId);
        if (paymentOpt.isPresent()) {
            Payment payment = paymentOpt.get();
            payment.setStatus(status);
            return paymentRepo.save(payment);
        }
        throw new RuntimeException("Payment not found with id: " + paymentId);
    }
    
    @Override
    public Payment processPayment(Long paymentId) {
        Optional<Payment> paymentOpt = paymentRepo.findById(paymentId);
        if (paymentOpt.isPresent()) {
            Payment payment = paymentOpt.get();
            
            if (payment.getStatus() != Payment.PaymentStatus.PENDING) {
                throw new RuntimeException("Only pending payments can be processed");
            }
            
            // Simulate payment processing logic
            payment.setStatus(Payment.PaymentStatus.COMPLETED);
            payment.setPaymentDate(LocalDateTime.now());
            
            return paymentRepo.save(payment);
        }
        throw new RuntimeException("Payment not found with id: " + paymentId);
    }
    
    @Override
    public Payment refundPayment(Long paymentId) {
        Optional<Payment> paymentOpt = paymentRepo.findById(paymentId);
        if (paymentOpt.isPresent()) {
            Payment payment = paymentOpt.get();
            
            if (payment.getStatus() != Payment.PaymentStatus.COMPLETED) {
                throw new RuntimeException("Only completed payments can be refunded");
            }
            
            payment.setStatus(Payment.PaymentStatus.REFUNDED);
            return paymentRepo.save(payment);
        }
        throw new RuntimeException("Payment not found with id: " + paymentId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public BigDecimal getTotalCompletedPayments() {
        BigDecimal total = paymentRepo.getTotalCompletedPayments();
        return total != null ? total : BigDecimal.ZERO;
    }
    
    @Override
    @Transactional(readOnly = true)
    public BigDecimal getTotalPaidAmountForBooking(Booking booking) {
        BigDecimal total = paymentRepo.getTotalPaidAmountForBooking(booking);
        return total != null ? total : BigDecimal.ZERO;
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<Payment> getPaymentsWithPagination(Pageable pageable) {
        return paymentRepo.findAll(pageable);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<Payment> getPaymentsByStatus(Payment.PaymentStatus status, Pageable pageable) {
        return paymentRepo.findByStatus(status, pageable);
    }
    
    @Override
    @Transactional(readOnly = true)
    public long getPaymentsCountByStatus(Payment.PaymentStatus status) {
        return paymentRepo.countByStatus(status);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<Payment> getPaymentByTransactionId(String transactionId) {
        return paymentRepo.findByTransactionId(transactionId);
    }
}