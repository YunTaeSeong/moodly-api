package com.moodly.user.controller;

import com.moodly.common.security.principal.AuthPrincipal;
import com.moodly.user.request.MyChangePasswordRequest;
import com.moodly.user.service.MyPasswordChangeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/mypage/security")
@RequiredArgsConstructor
@Slf4j
public class MyPasswordChangeApiController {

    private final MyPasswordChangeService passwordChangeService;

    @PatchMapping("/changePassword")
    public ResponseEntity<Void> changePassword(
            @Valid
            @RequestBody MyChangePasswordRequest request,
            @AuthenticationPrincipal AuthPrincipal principal,
            @RequestHeader("Authorization") String authorization
    ) {
        log.info("[HIT] controller Authorization={}", authorization);
        passwordChangeService.changePassword(
                principal.getUserId(),
                request.currentPassword(),
                request.newPassword(),
                request.newPasswordConfirm(),
                authorization
        );

        return ResponseEntity.ok().build();
    }
}
