package com.moodly.product.controller;

import com.moodly.common.security.principal.AuthPrincipal;
import com.moodly.product.dto.WishListDto;
import com.moodly.product.response.WishListResponse;
import com.moodly.product.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlist")
@RequiredArgsConstructor
public class WishListApiController {

    private final WishListService wishListService;

    @PostMapping("/{productId}")
    public WishListResponse createWishList(
            @AuthenticationPrincipal AuthPrincipal principal,
            @PathVariable Long productId
    ) {
        WishListDto dto = wishListService.createWishList(principal.getUserId(), productId);
        return WishListResponse.response(dto);
    }

    @DeleteMapping("/{productId}")
    public void deleteWishList(
            @AuthenticationPrincipal AuthPrincipal principal,
            @PathVariable Long productId
    ) {
        wishListService.deleteWishList(principal.getUserId(), productId);
    }

    @GetMapping("/{productId}")
    public WishListResponse getWishList(
            @AuthenticationPrincipal AuthPrincipal principal,
            @PathVariable Long productId
    ) {
        WishListDto dto = wishListService.getWishList(principal.getUserId(), productId);
        return WishListResponse.response(dto);
    }

    @GetMapping("/all")
    public List<WishListResponse> getAllWishList(
            @AuthenticationPrincipal AuthPrincipal principal
    ) {
        List<WishListDto> allWishList = wishListService.getAllWishList(principal.getUserId());
        return allWishList.stream()
                .map(WishListResponse::response)
                .toList();
    }

}
