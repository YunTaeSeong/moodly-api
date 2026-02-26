package com.moodly.coupon.repository;

import com.moodly.coupon.entity.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserCouponRepository extends JpaRepository<UserCoupon, Long> {
    Optional<UserCoupon> findByIdAndUserId(Long id, Long userId);

    List<UserCoupon> findAllByUserId(Long userId);

    boolean existsByUserIdAndCouponId(Long userId, Long couponId);
}
