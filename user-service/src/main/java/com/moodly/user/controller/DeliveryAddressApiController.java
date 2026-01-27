package com.moodly.user.controller;

import com.moodly.common.security.principal.AuthPrincipal;
import com.moodly.user.dto.DeliveryAddressDto;
import com.moodly.user.request.DeliveryAddressCreateRequest;
import com.moodly.user.request.DeliveryAddressUpdateRequest;
import com.moodly.user.response.DeliveryAddressResponse;
import com.moodly.user.service.DeliveryAddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/delivery-addresses")
@RequiredArgsConstructor
@Slf4j
public class DeliveryAddressApiController {

    private final DeliveryAddressService deliveryAddressService;

    /**
     * 배송지 생성
     */
    @PostMapping
    public ResponseEntity<DeliveryAddressResponse> create(
            @Valid @RequestBody DeliveryAddressCreateRequest request,
            @AuthenticationPrincipal AuthPrincipal principal
    ) {
        DeliveryAddressDto dto = deliveryAddressService.create(
                principal.getUserId(),
                request.getPostcode(),
                request.getAddress(),
                request.getDetailAddress(),
                request.getRecipient(),
                request.getPhoneNumber(),
                request.getIsDefault()
        );
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(DeliveryAddressResponse.response(dto));
    }

    /**
     * 배송지 모든 목록 조회
     */
    @GetMapping
    public ResponseEntity<List<DeliveryAddressResponse>> findAllByUserId(
            @AuthenticationPrincipal AuthPrincipal principal
    ) {
        List<DeliveryAddressDto> dtos = deliveryAddressService.findAllByUserId(principal.getUserId());
        List<DeliveryAddressResponse> responses = dtos.stream()
                .map(DeliveryAddressResponse::response)
                .toList();
        return ResponseEntity.ok(responses);
    }

    /**
     * 배송지 단건 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity<DeliveryAddressResponse> findByIdAndUserId(
            @PathVariable Long id,
            @AuthenticationPrincipal AuthPrincipal principal
    ) {
        DeliveryAddressDto dto = deliveryAddressService.findByIdAndUserId(id, principal.getUserId());
        return ResponseEntity.ok(DeliveryAddressResponse.response(dto));
    }

    /**
     * 배송지 수정
     */
    @PutMapping("/{id}")
    public ResponseEntity<DeliveryAddressResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody DeliveryAddressUpdateRequest request,
            @AuthenticationPrincipal AuthPrincipal principal
    ) {
        DeliveryAddressDto dto = deliveryAddressService.update(
                id,
                principal.getUserId(),
                request.getPostcode(),
                request.getAddress(),
                request.getDetailAddress(),
                request.getRecipient(),
                request.getPhoneNumber()
        );
        return ResponseEntity.ok(DeliveryAddressResponse.response(dto));
    }

    /**
     * 배송지 삭제
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            @AuthenticationPrincipal AuthPrincipal principal
    ) {
        deliveryAddressService.delete(id, principal.getUserId());
        return ResponseEntity.noContent().build();
    }

    /**
     * 기존 배송지 설정
     */
    @PatchMapping("/{id}/default")
    public ResponseEntity<DeliveryAddressResponse> deliveryAddressDefault(
            @PathVariable Long id,
            @AuthenticationPrincipal AuthPrincipal principal
    ) {
        DeliveryAddressDto dto = deliveryAddressService.deliveryAddressDefault(id, principal.getUserId());
        return ResponseEntity.ok(DeliveryAddressResponse.response(dto));
    }
}
