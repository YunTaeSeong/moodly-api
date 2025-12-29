package com.moodly.common.security.principal;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class AuthPrincipal {

    // JWT sub
    private final String userId;

    private final String email;

    private final List<String> roles;
}
