package com.moodly.user.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryAddressCreateRequest {

    @NotBlank(message = "우편번호는 필수입니다.")
    private String postcode;

    @NotBlank(message = "주소는 필수입니다.")
    private String address;

    private String detailAddress;

    @NotBlank(message = "수령인은 필수입니다.")
    private String recipient;

    @NotBlank(message = "전화번호는 필수입니다.")
    private String phoneNumber;

    @NotNull(message = "기본 배송지 여부는 필수입니다.")
    private Boolean isDefault;
}
