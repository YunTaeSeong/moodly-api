package com.moodly.product.response;

import com.moodly.product.dto.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {

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

    public static ProductResponse response(ProductDto dto) {
        return ProductResponse.builder()
                .id(dto.getId())
                .name(dto.getName())
                .price(dto.getPrice())
                .discount(dto.getDiscount())
                .image(dto.getImage())
                .description(dto.getDescription())
                .details(dto.getDetails())
                .categoryId(dto.getCategoryId())
                .categoryName(dto.getCategoryName())
                .subCategoryId(dto.getSubCategoryId())
                .subCategoryName(dto.getSubCategoryName())
                .rating(dto.getRating())
                .reviewCount(dto.getReviewCount())
                .build();
    }

}
