package com.moodly.coupon.repository;

import com.moodly.coupon.entity.Coupon;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    Optional<Coupon> findFirstByName(String name);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM Coupon c WHERE c.id = :id")
    Optional<Coupon> findByWithLock(@Param("id") Long id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Coupon c SET c.minPurchase = :minPurchase WHERE c.name = :name")
    int updateMinPurchaseByName(@Param("name") String name, @Param("minPurchase") BigDecimal minPurchase);

}
