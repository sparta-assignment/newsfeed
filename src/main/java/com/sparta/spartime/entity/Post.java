package com.sparta.spartime.entity;

import com.sparta.spartime.dto.request.PostRequestDto;
import com.sparta.spartime.entity.common.TimeStamp;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "posts")
public class Post extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    private String title;

    private String contents;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private Type type;

    public enum Type {
        ANONYMOUS,
        NORMAL,
        NOTICE
    }

    public Post() {
    }

    public Post(PostRequestDto requestDto,Type type,User user) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContent();
        this.type = type;
        this.user = user;
    }

    public void update(PostRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContent();
    }
}
