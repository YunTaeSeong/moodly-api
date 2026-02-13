package com.moodly.notification.repository;

import com.moodly.notification.domain.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface NotificationRepository extends NotificationRepositoryCustom, JpaRepository<Notification, Long> {
    Optional<Notification> findByEventId(String eventId);
    boolean existsByEventId(String eventId);
    
    @Query("SELECT COUNT(n) FROM Notification n WHERE n.userId = :userId AND n.isRead = false")
    long countUnreadByUserId(@Param("userId") Long userId);
}
