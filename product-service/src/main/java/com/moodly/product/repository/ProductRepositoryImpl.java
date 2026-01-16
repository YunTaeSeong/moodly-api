package com.moodly.product.repository;

import com.moodly.product.domain.Product;
import com.moodly.product.domain.QProduct;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.moodly.product.domain.QProduct.product;

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
}
