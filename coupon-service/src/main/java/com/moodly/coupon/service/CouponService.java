package com.moodly.coupon.service;

import com.moodly.common.exception.BaseException;
import com.moodly.common.exception.GlobalErrorCode;
import com.moodly.common.security.principal.AuthPrincipal;
import com.moodly.coupon.dto.CouponDto;
import com.moodly.coupon.dto.UserCouponDto;
import com.moodly.coupon.entity.Coupon;
import com.moodly.coupon.entity.UserCoupon;
import com.moodly.coupon.repository.CouponRepository;
import com.moodly.coupon.repository.UserCouponRepository;
import com.moodly.coupon.request.CouponCreateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CouponService {

    private final CouponRepository couponRepository;
    private final UserCouponRepository userCouponRepository;
    private static final String ORDER_ID_PREFIX = "ORDER-";

    // 쿠폰 조회
    @Transactional(readOnly = true)
    public UserCouponDto getUserCoupon(Long userId, Long userCouponId) {
        UserCoupon userCoupon = getOwnedUserCoupon(userId, userCouponId);

        return UserCouponDto.fromEntity(userCoupon);
    }

    // 쿠폰 전체 조회
    @Transactional(readOnly = true)
    public List<UserCouponDto> getAllUserCoupon(Long userId) {
        return userCouponRepository.findAllByUserId(userId)
                .stream()
                .map(UserCouponDto::fromEntity)
                .toList();
    }

    // 쿠폰 발행
    @Transactional
    public UserCouponDto issue(Long userId, Long couponId) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new BaseException(GlobalErrorCode.COUPON_NOT_FOUND));

        // 1. 발급 기간 검증
        coupon.validateIssuable();

        // 2. 중복 발급 방지 (DB + 사전 체크)
        if (userCouponRepository.existsByUserIdAndCouponId(userId, couponId)) {
            throw new BaseException(GlobalErrorCode.COUPON_ALREADY_ISSUED);
        }

        // 3. 수량 검증
        coupon.incrementIssuedQuantity();

        // 4. 만료일 계산
        LocalDateTime expiredAt = LocalDateTime.now().plusDays(coupon.getValidDays());

        // 5. orderId 생성
        String orderId = ORDER_ID_PREFIX + UUID.randomUUID().toString().substring(0, 8);

        UserCoupon userCoupon = UserCoupon.issue(userId, coupon.getId(), orderId, expiredAt);

        userCouponRepository.save(userCoupon);

        return UserCouponDto.fromEntity(userCoupon);
    }

    // 쿠폰 사용
    @Transactional
    public UserCouponDto use(
            Long userId,
            Long userCouponId,
            String orderId,
            BigDecimal orderAmount
    ) {
        UserCoupon userCoupon = getOwnedUserCoupon(userId, userCouponId);

        Coupon coupon = couponRepository.findById(userCoupon.getCouponId())
                .orElseThrow(() -> new BaseException(GlobalErrorCode.COUPON_NOT_FOUND));

        // 최소 주문 금액 검증
        coupon.validateUsable(orderAmount);

        userCoupon.use(orderId);

        return UserCouponDto.fromEntity(userCoupon);
    }

    // 쿠폰 취소
    @Transactional
    public UserCouponDto cancel(Long userId, Long userCouponId) {
        UserCoupon userCoupon = getOwnedUserCoupon(userId, userCouponId);

        userCoupon.cancel();

        return UserCouponDto.fromEntity(userCoupon);
    }

    // 쿠폰 만료
    @Transactional
    public void expire(Long userId, Long userCouponId) {
        UserCoupon userCoupon = getOwnedUserCoupon(userId, userCouponId);

        userCoupon.expire();
    }

    private UserCoupon getOwnedUserCoupon(Long userId, Long userCouponId) {
        return userCouponRepository.findByIdAndUserId(userCouponId, userId)
                .orElseThrow(() -> new BaseException(GlobalErrorCode.COUPON_NOT_FOUND));
    }


    // == 관리자 == //
    // 쿠폰 생성
    private void isAdmin(AuthPrincipal principal) {
        if(principal == null || !principal.isAdmin()) {
            throw new BaseException(GlobalErrorCode.MISSING_AUTHORIZATION);
        }
    }

    @Transactional
    public CouponDto createCoupon(
            AuthPrincipal principal,
            CouponCreateRequest request
    ) {
        isAdmin(principal);
        validateRequest(request);

        Coupon coupon = Coupon.builder()
                .name(request.getName())
                .discount(request.getDiscount())
                .discountType(request.getDiscountType())
                .minPurchase(
                        request.getMinPurchase() == null
                                ? BigDecimal.ZERO
                                : request.getMinPurchase()
                )
                .validStartDate(request.getValidStartDate())
                .validEndDate(request.getValidEndDate())
                .validDays(request.getValidDays())
                .totalQuantity(request.getTotalQuantity())
                .issuedQuantity(0)
                .build();

        couponRepository.save(coupon);

        return CouponDto.fromEntity(coupon);
    }

    private void validateRequest(CouponCreateRequest request) {

        // 기간 검증
        if (request.getValidStartDate() != null
                && request.getValidEndDate() != null
                && request.getValidStartDate().isAfter(request.getValidEndDate())) {
            throw new BaseException(GlobalErrorCode.INVALID_COUPON_PERIOD);
        }

        // 할인값 검증
        if (request.getDiscount() == null || request.getDiscount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BaseException(GlobalErrorCode.MIN_PURCHASE_NOT_MET);
        }

        // 수량 검증
        if (request.getTotalQuantity() != null && request.getTotalQuantity() < 0) {
            throw new BaseException(GlobalErrorCode.INVALID_COUPON_QUANTITY);
        }
    }

}
