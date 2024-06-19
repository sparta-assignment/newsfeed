package com.sparta.spartime.service;

import com.sparta.spartime.dto.request.UserSignupRequestDto;
import com.sparta.spartime.dto.response.UserResponseDto;
import com.sparta.spartime.entity.User;
import com.sparta.spartime.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserResponseDto signup(UserSignupRequestDto requestDto) {
        String email = requestDto.getEmail();
        String password = requestDto.getPassword();
        String nickname = requestDto.getNickname();
        String intro = requestDto.getIntro();

        if (userRepository.existsByEmailOrNickname(email, nickname)) {
            throw new IllegalArgumentException("이미 존재함~");
        }

        // TODO: password 는 encode 해야함
        User user = User.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .intro(intro)
                .role(User.Role.USER)
                .status(User.Status.ACTIVITY)
                .build();

        // return
        return new UserResponseDto(userRepository.save(user));
    }

}
