package com.sparta.spartime.entity;

import com.sparta.spartime.entity.common.TimeStamp;
import jakarta.persistence.*;

@Entity
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
}
