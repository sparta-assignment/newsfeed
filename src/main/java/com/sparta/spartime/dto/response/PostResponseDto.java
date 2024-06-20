package com.sparta.spartime.dto.response;

import com.sparta.spartime.entity.Post;

import java.time.LocalDateTime;

public class PostResponseDto {

    private Long id;
    private String title;
    private String content;
    private Long userId;
    private String nickname;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContents();
        this.userId = post.getUser().getId();
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();

    }

/*    public void PostResponseAnonymousDto() {

    }*/


}
