package com.moodly.user.repository;

import com.moodly.user.domain.Users;
import com.moodly.user.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByEmail(String email);

    @Query("select u from Users u where u.name = :name and u.phoneNumber = :phoneNumber")
    Optional<Users> findByNameAndPhoneNumber(String name, String phoneNumber);

    boolean existsByEmail(String email);

    List<Users> findByRole(UserRole role);
}
