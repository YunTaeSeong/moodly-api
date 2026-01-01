package com.moodly.user.request;

import com.moodly.common.exception.BaseException;
import com.moodly.common.exception.GlobalErrorCode;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequest {

    @Email(message = "아이디(이메일)은 필수입니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수입니다.")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-={}\\[\\]:;\"'<>,.?/\\\\|`~]).{8,20}$",
            message = "비밀번호는 8~20자이며 영문/숫자/특수문자를 각각 1개 이상 포함해야 합니다."
    )
    private String password;

    @NotBlank(message = "비밀번호가 다릅니다.")
    private String rePassword;

    @NotBlank(message = "이름은 필수입니다.")
    private String name;

    @NotBlank(message = "휴대폰 번호는 필수입니다.")
    @Pattern(
            regexp = "^01[016789]-\\d{4}-\\d{4}$",
            message = "휴대폰 번호는 010-1234-5678 형식이어야 합니다."
    )
    private String phoneNumber;
}
