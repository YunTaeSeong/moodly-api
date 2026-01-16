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

    private String categoryName;

    private Long subCategoryId;

    private String subCategoryName;

    private BigDecimal rating;

    private Integer reviewCount;

    public static ProductDto fromEntity(Product product, String categoryName, String subCategoryName) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .discount(product.getDiscount())
                .image(product.getImage())
                .description(product.getDescription())
                .details(product.getDetails())
                .categoryId(product.getCategoryId())
                .categoryName(categoryName)
                .subCategoryId(product.getSubCategoryId())
                .subCategoryName(subCategoryName)
                .rating(product.getRating())
                .build();
    }
}
