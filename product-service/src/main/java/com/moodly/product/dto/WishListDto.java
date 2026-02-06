package com.moodly.product.dto;

import com.moodly.product.domain.WishList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WishListDto {

    private Long id;
    private Long userId;
    private Long productId;
    private LocalDateTime createdAt;

    public static WishListDto fromEntity(WishList wishList) {
        return WishListDto.builder()
                .id(wishList.getId())
                .userId(wishList.getUserId())
                .productId(wishList.getProductId())
                .createdAt(wishList.getCreatedAt())
                .build();
    }
}
