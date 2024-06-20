package com.sparta.spartime.repository;

import com.sparta.spartime.entity.Comment;
import com.sparta.spartime.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<List<Comment>> findAllByPost_Id(Long postId);
    Optional<Comment> findByIdAndPost_Id(Long id, Long postId);
}
