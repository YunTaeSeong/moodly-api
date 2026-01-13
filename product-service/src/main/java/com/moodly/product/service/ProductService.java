package com.moodly.product.service;

import com.moodly.common.exception.BaseException;
import com.moodly.common.exception.GlobalErrorCode;
import com.moodly.product.domain.Product;
import com.moodly.product.dto.ProductDto;
import com.moodly.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductDto> getAllProducts() {
        List<Product> all = productRepository.findAll();
        return all.stream()
                .map(ProductDto::fromEntity)
                .toList();
    }

    public ProductDto getProductById(Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new BaseException(GlobalErrorCode.PRODUCTID_NOT_FOUND));

        return ProductDto.fromEntity(product);
    }
}
