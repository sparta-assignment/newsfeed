package com.sparta.spartime.service;

import com.sparta.spartime.dto.request.UserLoginRequestDto;
import com.sparta.spartime.dto.response.LoginResponseDto;
import com.sparta.spartime.entity.User;
import com.sparta.spartime.repository.UserRepository;
import com.sparta.spartime.security.principal.UserPrincipal;
import com.sparta.spartime.util.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    public LoginResponseDto login(UserLoginRequestDto requestDto) {
        /*
        1. AuthenticationManager 를 가져와야함 -> 따로 Bean 으로 등록해야함
        2. 가져온 다음 Authentication 객체를 만들어야함 -> 이 때 requestDto 의 email / password 를 사용
        3. Authentication 객체를 만들면 UserDetails 를 가져옴 -> UserDetailsService 와 UserDetails 인터페이스 를 구현해야함
        4. JWT 로 유저의 정보 (id / email) 를 claims 로 만듦
        5. 유저한테 전달

        // token -> id, email -> block / unblock
        // 유저의 정보를 우리가 사용하기 위해서 ->
         */
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDto.getEmail(),
                        requestDto.getPassword(),
                        null
                )
        );

        // 로그인 -> access token -> 이 과정에서 필터가 필요할까요?
        // email & password -> 올바른 user 를 가져오는 과정
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        User user =  userPrincipal.getUser();

        // token 생성
        /*
        1. access token
        2. refresh token
        3. user 에 refreshToken 세팅 후 저장
        3. LoginResponseDto 생성 후 토큰 세팅

         */

        String accessToken = jwtProvider.createAccessToken(user.getId(), user.getEmail(), user.getRole(), user.getStatus(),  user.getNickname());
        String refreshToken = jwtProvider.createRefreshToken();


        return new LoginResponseDto(accessToken, refreshToken);
    }
}
