package com.sparta.spartime.service;

import com.sparta.spartime.dto.request.UserEditProfileRequestDto;
import com.sparta.spartime.dto.request.UserSignupRequestDto;
import com.sparta.spartime.dto.request.UserWithdrawRequestDto;
import com.sparta.spartime.dto.response.UserResponseDto;
import com.sparta.spartime.entity.User;
import com.sparta.spartime.repository.UserRepository;
import com.sparta.spartime.security.principal.UserPrincipal;
import com.sparta.spartime.web.argumentResolver.annotation.LoginUser;
import lombok.RequiredArgsConstructor;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDto signup(UserSignupRequestDto requestDto) {
        String email = requestDto.getEmail();
        String password = requestDto.getPassword();
        String nickname = requestDto.getNickname();
        String intro = requestDto.getIntro();

        if (userRepository.existsByEmailOrNickname(email, nickname)) {
            throw new IllegalArgumentException("이미 존재함~");
        }

        User user = User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .nickname(nickname)
                .intro(intro)
                .role(User.Role.USER)
                .status(User.Status.ACTIVITY)
                .build();

        // return
        return new UserResponseDto(userRepository.save(user));
    }

    @Transactional
    public void withdraw(Long id, UserWithdrawRequestDto requestDto, UserPrincipal userPrincipal) {
        User idUser = findById(id);
        User pricipalUser = findByEmail(userPrincipal.getUsername());

        if (!idUser.getEmail().equals(pricipalUser.getEmail())) {
            throw new IllegalIdentifierException("요청된 사용자와 로그인된 사용자의 정보가 일치하지 않습니다.");
        }

        if (!passwordEncoder.matches(requestDto.getPassword(), pricipalUser.getPassword())) {
            throw new IllegalArgumentException("입력된 비밀번호가 사용자의 비밀번호와 일치하지 않습니다");
        }

        if (pricipalUser.getStatus().equals(User.Status.INACTIVITY)) {
            throw new IllegalArgumentException("이미 회원탈퇴된 사용자입니다.");
        }

        pricipalUser.withdraw();
    }

    @Transactional
    public UserResponseDto editProfile(Long id, UserEditProfileRequestDto requestDto, User loginUser) {
        if(!Objects.equals(id, loginUser.getId())) {
            throw new IllegalArgumentException("요청된 사용자와 로그인된 사용자의 정보가 일치하지 않습니다.");
        }

        User user = findById(id);

        if(!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("입력된 비밀번호가 사용자의 비밀번호와 일치하지 않습니다");
        }

        String newPassword = passwordEncoder.encode(requestDto.getNewPassword());
        String newNickname = requestDto.getNewNickname();
        String newIntro = requestDto.getNewIntro();

        user.editProfile(newPassword, newNickname, newIntro);

        return new UserResponseDto(user);
    }

    private User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("사용자를 찾을 수 없습니다.")
        );
    }

    private User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() ->
                new IllegalArgumentException("사용자를 찾을 수 없습니다.")
        );
    }
}
