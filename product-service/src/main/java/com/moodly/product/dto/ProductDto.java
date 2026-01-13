package com.moodly.product.dto;

import com.moodly.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {

    private Long id;

    private String name;

    private BigDecimal price;

    private Integer discount;

    private String image;

    private String description;

    private String details;

    private Long categoryId;

    private Long subCategoryId;

    private BigDecimal rating;

    private Integer reviewCount;

    public static ProductDto fromEntity(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .discount(product.getDiscount())
                .image(product.getImage())
                .description(product.getDescription())
                .details(product.getDetails())
                .categoryId(product.getCategoryId())
                .subCategoryId(product.getSubCategoryId())
                .rating(product.getRating())
                .build();
    }
}
