package com.sparta.spartime.repository;

import com.sparta.spartime.dto.response.CommentResponseDto;
import com.sparta.spartime.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentCustomRepository {
    Page<CommentResponseDto> findCommentByLikeId(User user, Pageable pageable);
}
