package com.moodly.product.repository;

import com.moodly.product.domain.Product;
import com.moodly.product.domain.QProduct;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private static final QProduct product = QProduct.product;

    @Override
    public List<Product> searchByName(String keyword) {
        return queryFactory
                .selectFrom(product)
                .where(product.name.startsWithIgnoreCase(keyword))
                .orderBy(product.name.asc())
                .limit(20)
                .fetch();
    }

    @Override
    public List<Product> findHotDealProducts(int limit) {
        return queryFactory
                .selectFrom(product)
                .where(product.discount.gt(0))
                .orderBy(product.discount.desc()) // 할인율 높은 순
                .limit(limit)
                .fetch();
    }

    @Override
    public List<Product> findTodaySpecialProducts(int limit) {
        return queryFactory
                .selectFrom(product)
                .where(product.discount.gt(0))
                .orderBy(product.createdDate.desc()) // 최신순
                .limit(limit)
                .fetch();
    }
}
