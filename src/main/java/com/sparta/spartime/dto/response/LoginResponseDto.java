package com.sparta.spartime.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginResponseDto {
    private String accessToken;
    private String refreshToken;
}

