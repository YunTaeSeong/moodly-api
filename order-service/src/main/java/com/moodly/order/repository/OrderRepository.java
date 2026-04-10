package com.moodly.order.repository;

import com.moodly.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    /** 주문 전체 조회 (최신순) */
    List<Order> findAllByUserIdOrderByCreatedDateDesc(Long userId);

    Optional<Order> findByOrderId(String orderId);
}
