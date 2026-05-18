package com.moodly.order.repository;

import com.moodly.order.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findByOrderId(Long orderId);

    @Query("SELECT oi FROM OrderItem oi JOIN FETCH oi.order WHERE oi.id = :id")
    Optional<OrderItem> findByIdWithOrder(@Param("id") Long id);
}
