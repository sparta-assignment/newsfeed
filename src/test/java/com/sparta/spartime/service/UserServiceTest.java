package com.sparta.spartime.service;

import com.sparta.spartime.dto.response.UserProfileResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class UserServiceTest {

    @Autowired UserService userService;

    @DisplayName("유저 프로필에 좋아요수 리턴")
    @Test
    void test1() {
        UserProfileResponseDto profile = userService.getProfile(1L);
        assertEquals(profile.getPostLikeCount(), 300L);
    }
}