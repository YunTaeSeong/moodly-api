package com.moodly.notification.repository;

import com.moodly.notification.domain.Notification;
import com.moodly.notification.domain.QNotification;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private static final QNotification notification = QNotification.notification;

    @Override
    public Page<Notification> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable) {
        List<Notification> content = queryFactory
                .selectFrom(notification)
                .where(notification.userId.eq(userId))
                .orderBy(notification.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(notification.count())
                .from(notification)
                .where(notification.userId.eq(userId))
                .fetchOne();

        return new PageImpl<>(content, pageable, total == null ? 0 : total);
    }
}
