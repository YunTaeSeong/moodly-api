package com.moodly.product.response;

import com.moodly.product.dto.SubCategoryDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubCategoryResponse {

    private Long id;
    private Long categoryId;
    private String name;
    private Integer displayOrder;

    public static SubCategoryResponse response(SubCategoryDto dto) {
        return SubCategoryResponse.builder()
                .id(dto.getId())
                .categoryId(dto.getCategoryId())
                .name(dto.getName())
                .displayOrder(dto.getDisplayOrder())
                .build();
    }
}

