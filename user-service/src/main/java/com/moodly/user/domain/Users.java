package com.moodly.user.domain;

import com.moodly.common.domain.BaseEntity;
import com.moodly.user.enums.Provider;
import com.moodly.user.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.repository.Modifying;

@Entity
@Table(name = "users",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_provider_provider_id", columnNames = {"provider", "provider_id"})
    },
    indexes = {
        @Index(name = "idx_users_email", columnList = "email")
    }
)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Users extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true, length = 50)
    private String email;

    @Column(nullable = true)
    private String password;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = true, length = 20)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private Provider provider = Provider.LOCAL;

    @Column(nullable = true, length = 100)
    private String providerId;

    // 일반 로그인용 팩토리 메소드
    public static Users of(
            String email,
            String password,
            String name,
            String phoneNumber
    ) {
        return Users.builder()
                .email(email)
                .password(password)
                .name(name)
                .phoneNumber(phoneNumber)
                .role(UserRole.USER)
                .provider(Provider.LOCAL)
                .build();
    }

    // 카카오 로그인용 팩토리 메소드
    public static Users ofKakao(
            String providerId,
            String name,
            String email
    ) {
        return Users.builder()
                .providerId(providerId)
                .name(name)
                .email(email)  // 카카오에서 이메일 제공 시
                .provider(Provider.KAKAO)
                .role(UserRole.USER)
                .password(null)  // 소셜 로그인은 비밀번호 없음
                .build();
    }

    public void changePassword(String encodedPassword) {
        if (this.provider != Provider.LOCAL) {
            throw new IllegalStateException("소셜 로그인 사용자는 비밀번호를 변경할 수 없습니다.");
        }
        this.password = encodedPassword;
    }
}
