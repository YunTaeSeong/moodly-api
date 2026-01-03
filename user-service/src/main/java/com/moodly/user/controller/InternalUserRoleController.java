package com.moodly.user.controller;

import com.moodly.user.service.InternalUserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/user")
@Validated
public class InternalUserRoleController {

    private final InternalUserRoleService userRoleService;

    @GetMapping("/{userId}/roles")
    public List<String> getRoles(@PathVariable Long userId) {
        return userRoleService.getRoles(userId);
    }
}
