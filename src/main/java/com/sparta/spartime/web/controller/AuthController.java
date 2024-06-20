package com.sparta.spartime.web.controller;

import com.sparta.spartime.aop.envelope.Envelope;
import com.sparta.spartime.dto.request.UserLoginRequestDto;
import com.sparta.spartime.dto.response.LoginResponseDto;
import com.sparta.spartime.security.principal.UserPrincipal;
import com.sparta.spartime.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @Envelope
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody UserLoginRequestDto requestDto) {
        return ResponseEntity.ok(authService.login(requestDto));
    }

    @PostMapping("/reissue")
    public ResponseEntity<?> reIssueAccessToken() {
        return null;
    }

    @GetMapping("/test")
    public ResponseEntity<?> test(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        log.info("id: {}", userPrincipal.getUser().getId());
        log.info("email: {}", userPrincipal.getUser().getEmail());
        log.info("nickname: {}", userPrincipal.getUser().getNickname());
        log.info("status: {}", userPrincipal.getUser().getStatus());
        return ResponseEntity.ok("");
    }
}
