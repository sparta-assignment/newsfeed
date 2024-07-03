package com.sparta.spartime.entity;

import com.sparta.spartime.entity.common.TimeStamp;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "comments")
@Getter
public class Comment extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String contents;

    @Column(columnDefinition = "bigint default 0")
    private Long likes;

    public void updateComment(String contents) {
        this.contents = contents;
    }

    public void incrementLikes() {
        if (likes == null) {
            likes = 0L;
        }
        this.likes++;
    }

    public void decrementLikes() {
        if (likes == null) {
            likes = 0L;
        }

        if (likes > 0) {
            this.likes--;
        }
    }
}
