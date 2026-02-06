package com.moodly.product.service;

import com.moodly.common.exception.BaseException;
import com.moodly.common.exception.GlobalErrorCode;
import com.moodly.product.domain.Product;
import com.moodly.product.domain.WishList;
import com.moodly.product.dto.WishListDto;
import com.moodly.product.repository.ProductRepository;
import com.moodly.product.repository.WishListRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WishListServiceTest {

    @InjectMocks
    private WishListService wishListService;

    @Mock
    private WishListRepository wishListRepository;

    @Mock
    private ProductRepository productRepository;

    @DisplayName("찜 추가 : 성공")
    @Test
    void 찜_추가_성공() {
        // given
        Long userId = 1L;
        Long productId = 2L;

        when(wishListRepository.findByUserIdAndProductId(userId, productId)).thenReturn(Optional.empty());
        when(productRepository.findById(productId)).thenReturn(Optional.of(mock(Product.class)));

        WishList wishList = mock(WishList.class);
        when(wishList.getUserId()).thenReturn(userId);
        when(wishList.getProductId()).thenReturn(productId);
        when(wishListRepository.save(any(WishList.class))).thenReturn(wishList);

        // when
        WishListDto result = wishListService.createWishList(userId, productId);

        // then
        assertNotNull(result);
        assertEquals(userId, result.getUserId());
        assertEquals(productId, result.getProductId());

        verify(wishListRepository, times(1)).findByUserIdAndProductId(userId, productId);
        verify(productRepository, times(1)).findById(productId);
        verify(wishListRepository, times(1)).save(any(WishList.class));
    }

    @DisplayName("찜 추가 : 실패(이미 존재하는 상품)")
    @Test
    void 찜_추가_실패_이미_존재하는_상품() {
        // given
        Long userId = 1L;
        Long productId = 2L;

        WishList wishList = mock(WishList.class);
        when(wishListRepository.findByUserIdAndProductId(userId, productId)).thenReturn(Optional.of(wishList));

        // when
        BaseException baseException = assertThrows(
                BaseException.class,
                () -> wishListService.createWishList(userId, productId)
        );

        // then
        assertEquals(GlobalErrorCode.WISHLIST_ALREADY_EXISTS, baseException.getErrorCode());
        verify(wishListRepository, times(1)).findByUserIdAndProductId(userId, productId);
        verify(productRepository, never()).findById(any());
        verify(wishListRepository, never()).save(any());
    }

    @DisplayName("찜 단건 조회 : 성공")
    @Test
    void 단건_조회_성공() {
        // given
        Long userId = 1L;
        Long productId = 2L;

        WishList wishList = mock(WishList.class);
        when(wishList.getUserId()).thenReturn(userId);
        when(wishList.getProductId()).thenReturn(productId);
        when(wishListRepository.findByUserIdAndProductId(userId, productId)).thenReturn(Optional.of(wishList));

        // when
        WishListDto result = wishListService.getWishList(userId, productId);

        // then
        assertNotNull(result);
        assertEquals(userId, result.getUserId());
        assertEquals(productId, result.getProductId());
        verify(wishListRepository, times(1)).findByUserIdAndProductId(userId, productId);
    }

    @DisplayName("찜 단건 조회 : 실패(찜 목록 비어 있을 때)")
    @Test
    void 단건_조회_실패_상품_비어_있을_때() {
        // given
        Long userId = 1L;
        Long productId = 2L;

        when(wishListRepository.findByUserIdAndProductId(userId, productId)).thenReturn(Optional.empty());

        // when
        BaseException baseException = assertThrows(
                BaseException.class,
                () -> wishListService.getWishList(userId, productId)
        );

        // then
        assertEquals(GlobalErrorCode.WISHLIST_NOT_EXISTS, baseException.getErrorCode());
        verify(wishListRepository, times(1)).findByUserIdAndProductId(userId, productId);
    }

    @DisplayName("찜 전체 조회 : 성공")
    @Test
    void 찜_전체_조회_성공() {
        // given
        Long userId = 1L;
        WishList wishList = mock(WishList.class);
        when(wishList.getUserId()).thenReturn(userId);
        when(wishList.getProductId()).thenReturn(10L);
        List<WishList> wishLists = List.of(wishList);

        when(wishListRepository.findAllByUserId(userId)).thenReturn(wishLists);

        // when
        List<WishListDto> result = wishListService.getAllWishList(userId);

        // then
        assertNotNull(result);
        assertEquals(wishLists.size(), result.size());
        assertEquals(userId, result.get(0).getUserId());
        verify(wishListRepository, times(1)).findAllByUserId(userId);
    }

    @DisplayName("찜 전체 조회 : 실패(찜 목록 비어있을 때)")
    @Test
    void 찜_전체_조회_실패_찜_목록_비어_있을_때() {
        // given
        Long userId = 1L;

        WishList wishList = mock(WishList.class);

        when(wishListRepository.findAllByUserId(userId)).thenReturn(List.of());

        // when
        BaseException baseException = assertThrows(
                BaseException.class,
                () -> wishListService.getAllWishList(userId)
        );

        // then
        assertEquals(GlobalErrorCode.WISHLIST_NOT_EXISTS, baseException.getErrorCode());
        verify(wishListRepository, times(1)).findAllByUserId(userId);
    }

    @DisplayName("찜 삭제 : 성공")
    @Test
    void 찜_삭제_성공() {
        // given
        Long userId = 1L;
        Long productId = 2L;

        WishList wishList = mock(WishList.class);
        when(wishListRepository.findByUserIdAndProductId(userId, productId)).thenReturn(Optional.of(wishList));

        // when
        assertDoesNotThrow(
                () -> wishListService.deleteWishList(userId, productId)
        );

        // then
        verify(wishListRepository, times(1)).findByUserIdAndProductId(userId, productId);
        verify(wishListRepository, times(1)).delete(wishList);
    }

    @DisplayName("찜 삭제 : 실패(찜 목록 비어 있을 때)")
    @Test
    void 찜_삭제_실패_비어_있을_때() {
        // given
        Long userId = 1L;
        Long productId = 2L;

        when(wishListRepository.findByUserIdAndProductId(userId, productId)).thenReturn(Optional.empty());

        // when
        BaseException baseException = assertThrows(
                BaseException.class,
                () -> wishListService.deleteWishList(userId, productId)
        );

        // then
        assertEquals(GlobalErrorCode.WISHLIST_NOT_EXISTS, baseException.getErrorCode());
        verify(wishListRepository, times(1))
                .findByUserIdAndProductId(userId, productId);
        verify(wishListRepository, never()).delete(any());
    }

}