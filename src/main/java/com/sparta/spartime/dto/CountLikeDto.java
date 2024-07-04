package com.sparta.spartime.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public interface CountLikeDto {
    Long getPostLikeCount();
    Long getCommentLikeCount();
}
