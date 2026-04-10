package com.moodly.payment.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderPaymentCompleteFeignRequest {

    private String paymentKey;
}
