package com.sparta.spartime.web.controller;

import com.sparta.spartime.dto.request.UserSignupRequestDto;
import com.sparta.spartime.dto.response.UserResponseDto;
import com.sparta.spartime.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDto> signup(@Valid @RequestBody UserSignupRequestDto requestDto)  {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.signup(requestDto));
    }
}
