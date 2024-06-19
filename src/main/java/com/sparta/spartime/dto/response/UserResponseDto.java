package com.sparta.spartime.dto.response;

import com.sparta.spartime.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponseDto {
    private String email;
    private String nickname;
    private String intro;

    public UserResponseDto(User user) {
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.intro = user.getIntro();
    }
}
