package com.moodly.user.service;

import com.moodly.common.exception.BaseException;
import com.moodly.common.exception.GlobalErrorCode;
import com.moodly.common.util.EmailMasking;
import com.moodly.user.domain.Users;
import com.moodly.user.dto.FindIdConfirmDto;
import com.moodly.user.dto.FindIdDto;
import com.moodly.user.mail.EmailSender;
import com.moodly.user.repository.UserRepository;
import com.moodly.user.verification.FindIdVerificationStore;
import com.moodly.user.verification.FindIdVerificationValue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Duration;
import java.util.HexFormat;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class FindIdService {
    private final UserRepository userRepository;
    private final FindIdVerificationStore findIdVerificationStore;
    private final EmailSender emailSender;

    private static final Duration TTL = Duration.ofMinutes(5);
    private static final String REDIS_KEY_PREFIX = "findId:phoneNumber:";

    @Transactional
    public FindIdDto findId(String name, String phoneNumber) {
        String phone = regExPhone(phoneNumber);
        Optional<Users> usersOptional = userRepository.findByNameAndPhoneNumber(name, phone);

//        if(usersOptional.isEmpty()) {
//            return new FindIdDto(null);
//        }

        Users users = usersOptional.orElseThrow(
                () -> new BaseException(GlobalErrorCode.USER_NOT_FOUND));
        String email = users.getEmail();
        String masked = EmailMasking.mask(email);

        String code = digitCode();
        String codeHash = sha256Hex(code);

        String redisKey = REDIS_KEY_PREFIX + phone;

        // value에는 codeHash + email(원본) 저장
        findIdVerificationStore.save(redisKey, new FindIdVerificationValue(codeHash, email), TTL);

        // 메일 발송
        emailSender.sendFindIdCode(email, code);

        // 응답 : 이메일 마스킹
        return new FindIdDto(masked);
    }

    @Transactional
    public FindIdConfirmDto confirmFindId(String code, String phoneNumber) {
        String phone = regExPhone(phoneNumber);
        String redisKey = REDIS_KEY_PREFIX + phone;

        FindIdVerificationValue value = findIdVerificationStore.get(redisKey)
                .orElseThrow(() -> new BaseException(GlobalErrorCode.VERIFICATION_CODE_EXPIRED));

        String codeHash = sha256Hex(code);
        if(!value.codeHash().equals(codeHash)) {
            throw new BaseException(GlobalErrorCode.VERIFICATION_CODE_INVALID);
        }

        // 1회성
        findIdVerificationStore.delete(redisKey);

        return new FindIdConfirmDto(value.maskedEmail());
    }


    private String regExPhone(String phoneNumber) {
        if (phoneNumber == null) return null;
        return phoneNumber.replaceAll("[^0-9]", "");
    }

    private String digitCode() {
        int random = new Random().nextInt(900000) + 100000;
        return String.valueOf(random);
    }

    public String sha256Hex(String raw) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(raw.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(digest);
        } catch (Exception e) {
            throw new BaseException(GlobalErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

}
