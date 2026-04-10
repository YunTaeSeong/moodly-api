package com.moodly.payment.repository;

import com.moodly.payment.domain.Payment;
import com.moodly.payment.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    boolean existsByOrderIdAndStatus(String orderId, PaymentStatus status);

    Optional<Payment> findFirstByOrderIdAndStatus(String orderId, PaymentStatus status);
}
