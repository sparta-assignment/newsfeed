package com.sparta.spartime.web.controller;

import com.sparta.spartime.dto.request.UserSignupRequestDto;
import com.sparta.spartime.dto.request.UserWithdrawRequestDto;
import com.sparta.spartime.dto.response.UserResponseDto;
import com.sparta.spartime.security.principal.UserPrincipal;
import com.sparta.spartime.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDto> signup(@Valid @RequestBody UserSignupRequestDto requestDto)  {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.signup(requestDto));
    }

    @PostMapping("/{id}")
    public ResponseEntity<String> withdraw(@PathVariable Long id, @RequestBody UserWithdrawRequestDto requestDto, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        userService.withdraw(id, requestDto, userPrincipal);
        return ResponseEntity.status(HttpStatus.OK).body("회원탈퇴가 정상 처리되었습니다.");
    }
}
