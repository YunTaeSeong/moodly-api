package com.moodly.user.response;

import com.moodly.user.dto.DeliveryAddressDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryAddressResponse {

    private Long id;
    private Long userId;
    private String postcode;
    private String address;
    private String detailAddress;
    private String recipient;
    private String phoneNumber;
    private Boolean isDefault;

    public static DeliveryAddressResponse response(DeliveryAddressDto dto) {
        return DeliveryAddressResponse.builder()
                .id(dto.getId())
                .userId(dto.getUserId())
                .postcode(dto.getPostcode())
                .address(dto.getAddress())
                .detailAddress(dto.getDetailAddress())
                .recipient(dto.getRecipient())
                .phoneNumber(dto.getPhoneNumber())
                .isDefault(dto.getIsDefault())
                .build();
    }
}
