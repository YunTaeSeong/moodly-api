package com.moodly.product.repository;

import com.moodly.product.domain.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WishListRepository extends JpaRepository<WishList, Long> {
    Optional<WishList> findByUserIdAndProductId(Long userId, Long productId);
    List<WishList> findAllByUserId(Long userId);
}
