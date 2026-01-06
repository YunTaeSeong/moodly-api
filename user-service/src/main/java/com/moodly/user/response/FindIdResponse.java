package com.moodly.user.response;

import com.moodly.user.dto.FindIdDto;
import com.moodly.user.request.FindIdRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FindIdResponse {
    private String maskedEmail;

    public static FindIdResponse response(FindIdDto dto) {
        return FindIdResponse.builder()
                .maskedEmail(dto.maskedEmail())
                .build();
    }
}
