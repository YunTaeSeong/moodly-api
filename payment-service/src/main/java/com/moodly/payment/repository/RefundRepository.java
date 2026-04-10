package com.moodly.payment.repository;

import com.moodly.payment.domain.Refund;
import com.moodly.payment.enums.RefundStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefundRepository extends JpaRepository<Refund, Long> {

    boolean existsByPaymentIdAndStatus(Long paymentId, RefundStatus status);
}
