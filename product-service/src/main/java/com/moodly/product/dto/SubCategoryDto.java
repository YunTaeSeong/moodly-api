package com.moodly.product.dto;

import com.moodly.product.domain.SubCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubCategoryDto {

    private Long id;
    private Long categoryId;
    private String name;
    private Integer displayOrder;

    public static SubCategoryDto fromEntity(SubCategory subCategory) {
        return SubCategoryDto.builder()
                .id(subCategory.getId())
                .categoryId(subCategory.getCategoryId())
                .name(subCategory.getName())
                .displayOrder(subCategory.getDisplayOrder())
                .build();
    }
}

