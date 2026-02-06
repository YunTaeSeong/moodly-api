package com.moodly.product.service;

import com.moodly.common.exception.BaseException;
import com.moodly.common.exception.GlobalErrorCode;
import com.moodly.product.domain.WishList;
import com.moodly.product.dto.WishListDto;
import com.moodly.product.repository.ProductRepository;
import com.moodly.product.repository.WishListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class WishListService {

    private final WishListRepository wishListRepository;
    private final ProductRepository productRepository;

    /**
     * 찜 추가
     */
    @Transactional
    public WishListDto createWishList(Long userId, Long productId) {
        // 이미 찜한 상품인지 확인
        if (wishListRepository.findByUserIdAndProductId(userId, productId).isPresent()) {
            throw new BaseException(GlobalErrorCode.WISHLIST_ALREADY_EXISTS);
        }
        // 상품 존재 여부 확인
        productRepository.findById(productId)
                .orElseThrow(() -> new BaseException(GlobalErrorCode.PRODUCTID_NOT_FOUND));

        WishList saved = wishListRepository.save(WishList.of(userId, productId));

        log.info("WishList: id={}, userId={}, productId={}", saved.getId(), userId, productId);
        return WishListDto.fromEntity(saved);
    }

    /**
     * 찜 단건 조회
     */
    @Transactional(readOnly = true)
    public WishListDto getWishList(Long userId, Long productId) {
        // 찜 확인(없으면 예외)
        WishList wishList = wishListRepository.findByUserIdAndProductId(userId, productId)
                .orElseThrow(() -> new BaseException(GlobalErrorCode.WISHLIST_NOT_EXISTS));

        return WishListDto.fromEntity(wishList);
    }

    /**
     * 찜 전체 조회
     */
    @Transactional(readOnly = true)
    public List<WishListDto> getAllWishList(Long userId) {
        List<WishList> wishList = wishListRepository.findAllByUserId(userId);

        if (wishList == null || wishList.isEmpty()) {
            throw new BaseException(GlobalErrorCode.WISHLIST_NOT_EXISTS);
        }

        return wishList.stream()
                .map(WishListDto::fromEntity)
                .toList();
    }

    /**
     * 찜 삭제
     */
    @Transactional
    public void deleteWishList(Long userId, Long productId) {
        // 찜 확인(없으면 예외)
        WishList wishList = wishListRepository.findByUserIdAndProductId(userId, productId)
                .orElseThrow(() -> new BaseException(GlobalErrorCode.WISHLIST_NOT_EXISTS));

        wishListRepository.delete(wishList);
    }
}
