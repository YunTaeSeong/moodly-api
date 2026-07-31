package com.moodly.coupon.repository;

import com.moodly.coupon.entity.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserCouponRepository extends JpaRepository<UserCoupon, Long> {
    Optional<UserCoupon> findByIdAndUserId(Long id, Long userId);

    List<UserCoupon> findAllByUserId(Long userId);

    boolean existsByUserIdAndCouponId(Long userId, Long couponId);

    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM UserCoupon uc WHERE uc.couponId = :couponId")
    void deleteByCouponId(@Param("couponId") Long couponId);
}
