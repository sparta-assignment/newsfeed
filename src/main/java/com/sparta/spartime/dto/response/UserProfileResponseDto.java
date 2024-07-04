package com.sparta.spartime.dto.response;

import com.sparta.spartime.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class UserProfileResponseDto {
    private Long id;
    private String email;
    private String nickname;
    private String intro;
    private Long postLikeCount;
    private Long commentLikeCount;

    public UserProfileResponseDto(User user, Long postLikeCount, Long commentLikeCount) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.intro = user.getIntro();
        this.postLikeCount = postLikeCount;
        this.commentLikeCount = commentLikeCount;
    }
}