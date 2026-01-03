package com.moodly.user.service;

import com.moodly.common.exception.BaseException;
import com.moodly.common.exception.GlobalErrorCode;
import com.moodly.user.domain.Users;
import com.moodly.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class InternalUserRoleService {

    private final UserRepository userRepository;

    public List<String> getRoles(Long userId) {
        Users users = userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(GlobalErrorCode.USER_NOT_FOUND));

        return List.of(users.getRole().name());
    }
}
