package com.moodly.product.response;

import com.moodly.product.dto.WishListDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WishListResponse {

    private Long id;
    private Long userId;
    private Long productId;
    private LocalDateTime createdAt;

    public static WishListResponse response(WishListDto dto) {
        return WishListResponse.builder()
                .id(dto.getId())
                .userId(dto.getUserId())
                .productId(dto.getProductId())
                .createdAt(dto.getCreatedAt())
                .build();
    }
}
