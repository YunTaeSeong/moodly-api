package com.moodly.product.repository;

import com.moodly.product.domain.ProductInquiry;
import com.moodly.product.domain.QProductInquiry;
import com.moodly.product.enums.ProductInquiryStatus;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductInquiryRepositoryImpl implements ProductInquiryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private final QProductInquiry inquiry = QProductInquiry.productInquiry;

    @Override
    public Page<ProductInquiry> searchMyInquiries(Long userId, Long productId, ProductInquiryStatus status, String content, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder()
                .and(inquiry.userId.eq(userId));

        if (productId != null) builder.and(inquiry.productId.eq(productId));
        if (status != null) builder.and(inquiry.status.eq(status));
        if (content != null && !content.isBlank()) builder.and(inquiry.content.containsIgnoreCase(content));

        List<ProductInquiry> contents = queryFactory
                .selectFrom(inquiry)
                .where(builder)
                .orderBy(inquiry.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(inquiry.count())
                .from(inquiry)
                .where(builder)
                .fetchOne();

        return new PageImpl<>(contents, pageable, total == null ? 0 : total);
    }

    @Override
    public Page<ProductInquiry> searchAllAdminInquiries(Long productId, ProductInquiryStatus status, String content, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();

        if (productId != null) builder.and(inquiry.productId.eq(productId));
        if (status != null) builder.and(inquiry.status.eq(status));
        if (content != null && !content.isBlank()) builder.and(inquiry.content.containsIgnoreCase(content));

        List<ProductInquiry> contentList = queryFactory
                .selectFrom(inquiry)
                .where(builder)
                .orderBy(inquiry.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(inquiry.count())
                .from(inquiry)
                .where(builder)
                .fetchOne();

        return new PageImpl<>(contentList, pageable, total == null ? 0 : total);
    }
}
