package com.moodly.coupon.service;

import com.moodly.common.exception.BaseException;
import com.moodly.common.exception.GlobalErrorCode;
import com.moodly.coupon.dto.UserCouponDto;
import com.moodly.coupon.entity.Coupon;
import com.moodly.coupon.entity.UserCoupon;
import com.moodly.coupon.enums.UserCouponStatus;
import com.moodly.coupon.repository.CouponRepository;
import com.moodly.coupon.repository.UserCouponRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CouponServiceTest {

    @InjectMocks
    private CouponService couponService;

    @Mock
    private CouponRepository couponRepository;

    @Mock
    private UserCouponRepository userCouponRepository;

    @Test
    @DisplayName("쿠폰 발행 : 성공")
    void 쿠폰_발행_성공() {
        // given
        Long userId = 1L;
        Long couponId = 10L;

        Coupon coupon = mock(Coupon.class);

        when(couponRepository.findById(couponId)).thenReturn(Optional.of(coupon));

        when(userCouponRepository.existsByUserIdAndCouponId(userId, couponId)).thenReturn(false);

        when(coupon.getValidDays()).thenReturn(7);

        when(userCouponRepository.save(any(UserCoupon.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        UserCouponDto result = couponService.issue(userId, couponId);

        // then
        assertNotNull(result);

        verify(coupon).validateIssuable();
        verify(coupon).incrementIssuedQuantity();
        verify(userCouponRepository).save(any(UserCoupon.class));
    }

    @Test
    @DisplayName("쿠폰 발행 : 실패")
    void 쿠폰_발행_실패() {
        // given
        Long userId = 1L;
        Long couponId = 10L;

        when(couponRepository.findById(couponId)).thenReturn(Optional.empty());

        // when
        BaseException baseException = assertThrows(
                BaseException.class,
                () -> couponService.issue(userId, couponId)
        );

        // then
        assertEquals(GlobalErrorCode.COUPON_NOT_FOUND, baseException.getErrorCode());
        verify(couponRepository).findById(couponId);
    }

    @Test
    @DisplayName("쿠폰 발행 : 실패(중복발급)")
    void 쿠폰_발행_실패_중복_발급() {
        // given
        Long  userId = 1L;
        Long couponId = 10L;

        Coupon coupon = mock(Coupon.class);

        when(couponRepository.findById(couponId)).thenReturn(Optional.of(coupon));
        when(userCouponRepository.existsByUserIdAndCouponId(userId, couponId)).thenReturn(true);

        // when
        BaseException baseException = assertThrows(
                BaseException.class,
                () -> couponService.issue(userId, couponId)
        );

        // then
        assertEquals(GlobalErrorCode.COUPON_ALREADY_ISSUED, baseException.getErrorCode());
        verify(userCouponRepository).existsByUserIdAndCouponId(userId, couponId);
    }

    @Test
    @DisplayName("쿠폰 발행 : 수량 초과")
    void 쿠폰_발행_수량_초과() {
        // given
        Long userId = 1L;
        Long couponId = 10L;

        Coupon coupon = mock(Coupon.class);

        when(couponRepository.findById(couponId)).thenReturn(Optional.of(coupon));
        when(userCouponRepository.existsByUserIdAndCouponId(userId, couponId)).thenReturn(false);
        doThrow(new BaseException(GlobalErrorCode.COUPON_EXHAUSTED)).when(coupon).incrementIssuedQuantity();

        assertThrows(BaseException.class,
                () -> couponService.issue(userId, couponId));

        verify(coupon).incrementIssuedQuantity();
    }

    @Test
    @DisplayName("쿠폰 조회 : 단건")
    void 쿠폰_조회_단건() {
        // given
        Long userId = 1L;
        Long userCouponId = 100L;

        UserCoupon userCoupon = UserCoupon.issue(
                userId,
                10L,
                "ORDER-",
                LocalDateTime.now().plusDays(7)
        );

        when(userCouponRepository.findByIdAndUserId(userCouponId, userId)).thenReturn(Optional.of(userCoupon));

        // when
        UserCouponDto result = couponService.getUserCoupon(userId, userCouponId);

        // then
        assertNotNull(result);
        assertEquals(userId, result.getUserId());
        assertEquals(10L, result.getCouponId());

        verify(userCouponRepository)
                .findByIdAndUserId(userCouponId, userId);
    }

    @Test
    @DisplayName("쿠폰 조회 : 전체")
    void 쿠폰_조회_전체() {
        // given
        Long userId = 1L;

        UserCoupon coupon1 = UserCoupon.issue(
                userId,
                10L,
                "ORDER-",
                LocalDateTime.now().plusDays(7)
        );

        UserCoupon coupon2 = UserCoupon.issue(
                userId,
                20L,
                "ORDER-",
                LocalDateTime.now().plusDays(7)
        );

        when(userCouponRepository.findAllByUserId(userId)).thenReturn(List.of(coupon1, coupon2));

        // when
        List<UserCouponDto> result = couponService.getAllUserCoupon(userId);

        // then
        assertNotNull(result);
        assertEquals(2, result.size());

        assertEquals(10L, result.get(0).getCouponId());
        assertEquals(20L, result.get(1).getCouponId());

        verify(userCouponRepository).findAllByUserId(userId);
    }

    @Test
    @DisplayName("쿠폰 사용")
    void 쿠폰_사용() {
        // given
        Long userId = 1L;
        Long userCouponId = 10L;
        Long couponId = 99L;
        String orderId = "ORDER-";
        BigDecimal orderAmount = new BigDecimal("10000");

        Coupon coupon = mock(Coupon.class);

        UserCoupon userCoupon = UserCoupon.issue(
                userId,
                couponId,
                null,
                LocalDateTime.now().plusDays(7)
        );

        when(userCouponRepository.findByIdAndUserId(userCouponId, userId)).thenReturn(Optional.of(userCoupon));

        when(couponRepository.findById(couponId)).thenReturn(Optional.of(coupon));

        // when
        UserCouponDto result = couponService.use(userId, userCouponId, orderId, orderAmount);

        // then
        assertNotNull(result);
        verify(coupon).validateUsable(orderAmount);

        assertEquals(UserCouponStatus.USED, result.getStatus());
        assertEquals(orderId, result.getOrderId());
    }

    @Test
    @DisplayName("쿠폰 사용 취소")
    void 쿠폰_사용_취소() {
        // given
        Long userId = 1L;
        Long userCouponId = 10L;
        Long couponId = 99L;
        String orderId = "ORDER-";

        UserCoupon userCoupon = UserCoupon.issue(
                userId,
                couponId,
                null,
                LocalDateTime.now().plusDays(7)
        );

        userCoupon.use(orderId);
        when(userCouponRepository.findByIdAndUserId(userCouponId, userId)).thenReturn(Optional.of(userCoupon));

        // when
        UserCouponDto result = couponService.cancel(userId, userCouponId);

        // then
        assertNotNull(result);
        assertNull(userCoupon.getOrderId());
        assertEquals(UserCouponStatus.ISSUED, result.getStatus());
    }

    @Test
    @DisplayName("사용하지 않은 쿠폰 : 취소 실패")
    void 쿠폰_취소_실패() {
        Long userId = 1L;
        Long userCouponId = 10L;

        UserCoupon userCoupon = UserCoupon.issue(
                userId,
                20L,
                null,
                LocalDateTime.now().plusDays(7)
        );

        when(userCouponRepository.findByIdAndUserId(userCouponId, userId))
                .thenReturn(Optional.of(userCoupon));

        assertThrows(BaseException.class,
                () -> couponService.cancel(userId, userCouponId));
    }
}