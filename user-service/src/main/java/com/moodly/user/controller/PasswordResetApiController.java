package com.moodly.user.controller;

import com.moodly.user.request.PasswordConfirmRequest;
import com.moodly.user.request.PasswordResetRequest;
import com.moodly.user.service.PasswordResetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/password/reset")
public class PasswordResetApiController {

    private final PasswordResetService passwordResetService;

    /**
     * redis 키 저장 및 비밀번호 변경을 이메일로 발송
     */
    @PostMapping("/request")
    public ResponseEntity<Void> request(
            @Valid
            @RequestBody PasswordResetRequest request
    ) {
        passwordResetService.requestReset(request.email());
        return ResponseEntity.noContent().build();
    }

    /**
     * redis 저장 된 key 삭제 및 비밀번호 변경
     */
    @PostMapping("/confirm")
    public ResponseEntity<Void> confirm(
            @Valid
            @RequestBody PasswordConfirmRequest request
    ) {
        passwordResetService.confirmReset(request.token(), request.newPassword(), request.rePassword());
        return ResponseEntity.noContent().build();
    }
}
