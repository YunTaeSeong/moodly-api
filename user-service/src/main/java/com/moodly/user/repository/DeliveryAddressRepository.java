package com.moodly.user.repository;

import com.moodly.user.domain.DeliveryAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DeliveryAddressRepository extends JpaRepository<DeliveryAddress, Long> {

    @Query("SELECT d FROM DeliveryAddress d WHERE d.userId = :userId ORDER BY d.isDefault DESC, d.createdDate DESC")
    List<DeliveryAddress> findAllByUserId(Long userId);

    Optional<DeliveryAddress> findByIdAndUserId(Long id, Long userId);

    @Modifying
    @Query("UPDATE DeliveryAddress d SET d.isDefault = false WHERE d.userId = :userId")
    void clearDefaultByUserId(@Param("userId") Long userId);

    boolean existsByIdAndUserId(Long id, Long userId);
}
