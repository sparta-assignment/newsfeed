package com.sparta.spartime.repository;

import com.sparta.spartime.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmailOrNickname(String email, String nickname);
}
