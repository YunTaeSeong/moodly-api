package com.moodly.user.domain;

import com.moodly.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(
        name = "delivery_addresses",
        indexes = {
                @Index(name = "idx_delivery_user_id", columnList = "user_id")
        }
)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class DeliveryAddress extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(nullable = false, length = 10)
    private String postcode;

    @Column(nullable = false, length = 200)
    private String address;

    @Column(name = "detail_address", length = 200)
    private String detailAddress;

    @Column(nullable = false, length = 50)
    private String recipient;

    @Column(name = "phone_number", nullable = false, length = 20)
    private String phoneNumber;

    @Column(name = "is_default", nullable = false)
    private Boolean isDefault;

    public void updateDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public void update(
            String postcode,
            String address,
            String detailAddress,
            String recipient,
            String phoneNumber
    ) {
        this.postcode = postcode;
        this.address = address;
        this.detailAddress = detailAddress;
        this.recipient = recipient;
        this.phoneNumber = phoneNumber;
    }

    public static DeliveryAddress of(
            Long userId,
            String postcode,
            String address,
            String detailAddress,
            String recipient,
            String phoneNumber,
            Boolean isDefault
    ) {
        return DeliveryAddress.builder()
                .userId(userId)
                .postcode(postcode)
                .address(address)
                .detailAddress(detailAddress)
                .recipient(recipient)
                .phoneNumber(phoneNumber)
                .isDefault(isDefault)
                .build();
    }
}
