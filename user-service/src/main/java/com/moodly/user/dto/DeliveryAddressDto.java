package com.moodly.user.dto;

import com.moodly.user.domain.DeliveryAddress;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryAddressDto {

    private Long id;
    private Long userId;
    private String postcode;
    private String address;
    private String detailAddress;
    private String recipient;
    private String phoneNumber;
    private Boolean isDefault;

    public static DeliveryAddressDto fromEntity(DeliveryAddress deliveryAddress) {
        return DeliveryAddressDto.builder()
                .id(deliveryAddress.getId())
                .userId(deliveryAddress.getUserId())
                .postcode(deliveryAddress.getPostcode())
                .address(deliveryAddress.getAddress())
                .detailAddress(deliveryAddress.getDetailAddress())
                .recipient(deliveryAddress.getRecipient())
                .phoneNumber(deliveryAddress.getPhoneNumber())
                .isDefault(deliveryAddress.getIsDefault())
                .build();
    }
}
