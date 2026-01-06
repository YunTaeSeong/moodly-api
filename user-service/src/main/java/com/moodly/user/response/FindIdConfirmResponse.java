package com.moodly.user.response;

import com.moodly.user.dto.FindIdConfirmDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FindIdConfirmResponse {
    private String maskedEmail;

    public static FindIdConfirmResponse response(FindIdConfirmDto dto) {
        return FindIdConfirmResponse.builder()
                .maskedEmail(dto.maskedEmail())
                .build();
    }
}
