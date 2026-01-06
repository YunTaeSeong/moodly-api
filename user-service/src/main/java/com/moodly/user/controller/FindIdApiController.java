package com.moodly.user.controller;

import com.moodly.user.dto.FindIdConfirmDto;
import com.moodly.user.dto.FindIdDto;
import com.moodly.user.request.FindIdConfirmRequest;
import com.moodly.user.request.FindIdRequest;
import com.moodly.user.response.FindIdConfirmResponse;
import com.moodly.user.response.FindIdResponse;
import com.moodly.user.service.FindIdService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/find-id")
@RequiredArgsConstructor
public class FindIdApiController {
    private final FindIdService findIdService;

    /**
     * 이메일 마스킹 처리 - EmailMasking
     */
    @PostMapping("/request")
    public FindIdResponse findId(@RequestBody FindIdRequest request) {
        FindIdDto dto = findIdService.findId(request.name(), request.phoneNumber());
        return FindIdResponse.response(dto);
    }

    /**
     * 이메일 전체 확인 (마스킹 X)
     */
    @PostMapping("/confirm")
    public FindIdConfirmResponse confirm(@RequestBody FindIdConfirmRequest request) {
        FindIdConfirmDto dto = findIdService.confirmFindId(request.code(), request.phoneNumber());
        return FindIdConfirmResponse.response(dto);
    }
}
