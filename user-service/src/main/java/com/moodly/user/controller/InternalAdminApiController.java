package com.moodly.user.controller;

import com.moodly.user.enums.UserRole;
import com.moodly.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/internal/admin")
@RequiredArgsConstructor
public class InternalAdminApiController {

    private final UserRepository userRepository;

    @GetMapping("/user-ids")
    public ResponseEntity<List<Long>> getAdminUserIds() {
        List<Long> adminUserIds = userRepository.findByRole(UserRole.ADMIN)
                .stream()
                .map(user -> user.getId())
                .collect(Collectors.toList());
        return ResponseEntity.ok(adminUserIds);
    }
}
