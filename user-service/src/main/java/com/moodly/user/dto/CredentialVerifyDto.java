package com.moodly.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CredentialVerifyDto {

    private Long userId;
    private List<String> roles;
}
