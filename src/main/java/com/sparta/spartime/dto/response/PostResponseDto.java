package com.sparta.spartime.dto.response;

import com.sparta.spartime.entity.Post;
import com.sparta.spartime.entity.User;

import java.time.LocalDateTime;

public class PostResponseDto {

    private final Long id;
    private final String title;
    private final String content;
    private final Long userId;
    private final String nickname;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContents();
        this.userId = post.getUser().getId();
        this.nickname = post.getUser().getNickname();
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
    }

    public PostResponseDto(Post post, Post.Type type) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContents();
        this.userId = post.getUser().getId();
        this.nickname = null;
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
    }


}
