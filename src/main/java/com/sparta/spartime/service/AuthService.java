package com.sparta.spartime.service;

import com.sparta.spartime.dto.request.UserLoginRequestDto;
import com.sparta.spartime.dto.response.LoginResponseDto;
import com.sparta.spartime.entity.User;
import com.sparta.spartime.repository.UserRepository;
import com.sparta.spartime.security.principal.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    @Transactional
    public LoginResponseDto login(UserLoginRequestDto requestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDto.getEmail(),
                        requestDto.getPassword(),
                        null
                )
        );

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        User user =  userService.findByEmail(userPrincipal.getUsername());

        String accessToken = jwtService.createAccessToken(user.getId(), user.getEmail(), user.getRole(), user.getStatus(),  user.getNickname());
        String refreshToken = jwtService.createRefreshToken();

        user.addRefreshToken(refreshToken);

        return new LoginResponseDto(accessToken, refreshToken);
    }
}
