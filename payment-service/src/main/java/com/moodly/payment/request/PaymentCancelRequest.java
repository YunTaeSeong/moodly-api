package com.moodly.payment.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaymentCancelRequest {

    @NotBlank
    private String orderId;

    @Size(max = 200)
    private String cancelReason;
}
