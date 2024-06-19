package com.sparta.spartime.entity;

import com.sparta.spartime.entity.common.TimeStamp;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User extends TimeStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    @Column(unique = true)
    private String nickname;

    private String intro;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    private String refreshToken;

    private String recentPassword;

    @Enumerated(EnumType.STRING)
    private Status status = Status.ACTIVITY;

    public enum Role {
        USER,
        ADMIN
    }

    public enum Status {
        ACTIVITY,
        INACTIVITY,
        BLOCKED
    }

}
