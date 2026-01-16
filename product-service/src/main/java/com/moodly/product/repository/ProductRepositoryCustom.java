package com.moodly.product.repository;

import com.moodly.product.domain.Product;

import java.util.List;

public interface ProductRepositoryCustom {
    List<Product> searchByName(String keyword);
}
