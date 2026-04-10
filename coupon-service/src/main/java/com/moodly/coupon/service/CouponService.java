package com.moodly.coupon.service;

import com.moodly.common.exception.BaseException;
import com.moodly.common.exception.GlobalErrorCode;
import com.moodly.common.security.principal.AuthPrincipal;
import com.moodly.coupon.config.CouponWelcomeProperties;
import com.moodly.coupon.dto.CouponDto;
import com.moodly.coupon.dto.UserCouponDto;
import com.moodly.coupon.entity.Coupon;
import com.moodly.coupon.entity.UserCoupon;
import com.moodly.coupon.repository.CouponRepository;
import com.moodly.coupon.repository.UserCouponRepository;
import com.moodly.coupon.request.CouponCreateRequest;
import com.moodly.coupon.response.UserCouponDetailResponse;
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
    private final CouponWelcomeProperties welcomeProperties;
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


    // 결제하기 -> 할인/쿠폰 변경 에 적용
    @Transactional(readOnly = true)
    public List<UserCouponDetailResponse> getAllUserCouponDetails(Long userId) {
        return userCouponRepository.findAllByUserId(userId).stream()
                .map(uc -> {
                    Coupon coupon = couponRepository.findById(uc.getCouponId())
                            .orElseThrow(() -> new BaseException(GlobalErrorCode.COUPON_NOT_FOUND));
                    return UserCouponDetailResponse.from(uc, coupon);
                })
                .toList();
    }

    // 받을 수 있는 쿠폰
    @Transactional(readOnly = true)
    public List<CouponDto> getReceivableCoupons(Long userId) {
        Coupon welcome = couponRepository.findFirstByName(welcomeProperties.getCouponName()).orElse(null);
        if (welcome == null) {
            return List.of();
        }
        if (userCouponRepository.existsByUserIdAndCouponId(userId, welcome.getId())) {
            return List.of();
        }
        try {
            welcome.validateIssuable();
        } catch (BaseException e) {
            return List.of();
        }
        return List.of(CouponDto.fromEntity(welcome));
    }

    /**
     * 회원가입, 카카오 최초 가입 시 user-service에서 호출, 이미 발급된 경우 없음
     */
    @Transactional
    public void issueWelcomeCouponForNewUser(Long userId) {
        Coupon coupon = couponRepository.findFirstByName(welcomeProperties.getCouponName())
                .orElseThrow(() -> new BaseException(GlobalErrorCode.COUPON_NOT_FOUND));

        if (userCouponRepository.existsByUserIdAndCouponId(userId, coupon.getId())) {
            log.debug("[CouponService] 신규회원 쿠폰 이미 보유 userId={}", userId);
            return;
        }

        coupon.validateIssuable();
        coupon.incrementIssuedQuantity();

        LocalDateTime expiredAt = LocalDateTime.now().plusDays(coupon.getValidDays());
        String orderId = ORDER_ID_PREFIX + UUID.randomUUID().toString().substring(0, 8);

        UserCoupon userCoupon = UserCoupon.issue(userId, coupon.getId(), orderId, expiredAt);
        userCouponRepository.save(userCoupon);
        log.info("[CouponService] 신규회원 쿠폰 발급 완료 userId={}, couponId={}", userId, coupon.getId());
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
            throw new BaseException(GlobalErrorCode.DUPLICATED_COUPON_ISSUE);
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

    /**
     * payment-service: 주문에 연결된 사용자 쿠폰(user_coupons.id)이 Toss 승인 전에 사용 가능한지 검증 (DB 변경 없음).
     * orderProductTotalAmount는 주문의 상품 합계(할인 전, order-service orders.total_amount)
     */
    @Transactional(readOnly = true)
    public void validateUserCouponForOrderPayment(Long userId, Long userCouponId, BigDecimal orderProductTotalAmount) {
        UserCoupon userCoupon = userCouponRepository.findByIdAndUserId(userCouponId, userId)
                .orElseThrow(() -> new BaseException(GlobalErrorCode.COUPON_NOT_FOUND));
        userCoupon.assertPayableBeforeCharge();
        Coupon coupon = couponRepository.findById(userCoupon.getCouponId())
                .orElseThrow(() -> new BaseException(GlobalErrorCode.COUPON_NOT_FOUND));
        coupon.validateUsable(orderProductTotalAmount);
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
