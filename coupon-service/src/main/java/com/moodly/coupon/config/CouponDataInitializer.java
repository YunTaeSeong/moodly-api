package com.moodly.coupon.config;

import com.moodly.coupon.entity.Coupon;
import com.moodly.coupon.enums.DiscountType;
import com.moodly.coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

// 신규회원 쿠폰 (전 상품 10%, 최소 주문 15,000원, 발급일 기준 30일)
@Slf4j
@Component
@RequiredArgsConstructor
public class CouponDataInitializer implements ApplicationRunner {

    private static final BigDecimal WELCOME_MIN_PURCHASE = new BigDecimal("15000");

    private final CouponRepository couponRepository;
    private final CouponWelcomeProperties welcomeProperties;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        String name = welcomeProperties.getCouponName();
        if (couponRepository.findFirstByName(name).isPresent()) {
            int updated = couponRepository.updateMinPurchaseByName(name, WELCOME_MIN_PURCHASE);
            if (updated > 0) {
                log.info("[CouponDataInitializer] 신규회원 쿠폰 최소주문금액 갱신: {}원 name={}", WELCOME_MIN_PURCHASE, name);
            }
            return;
        }
        Coupon coupon = Coupon.builder()
                .name(name)
                .discount(new BigDecimal("10"))
                .discountType(DiscountType.PERCENT)
                .minPurchase(WELCOME_MIN_PURCHASE)
                .validStartDate(null)
                .validEndDate(null)
                .validDays(30)
                .totalQuantity(null)
                .issuedQuantity(0)
                .build();
        couponRepository.save(coupon);
        log.info("[CouponDataInitializer] 신규회원 쿠폰 마스터 등록: name={}", name);
    }
}
