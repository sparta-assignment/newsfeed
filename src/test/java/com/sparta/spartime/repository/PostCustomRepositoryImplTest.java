package com.sparta.spartime.repository;

import com.sparta.spartime.SpartimeApplicationTests;
import com.sparta.spartime.dto.response.CommentResponseDto;
import com.sparta.spartime.dto.response.PostResponseDto;
import com.sparta.spartime.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@Transactional
class PostCustomRepositoryImplTest extends SpartimeApplicationTests {
    private User loginUser;

    @Autowired PostRepository postRepository;
    @Autowired CommentRepository commentRepository;
    @Autowired UserRepository userRepository;
    @Autowired LikeRepository likeRepository;

    @BeforeEach
    void setUp() {
        if (userRepository.count() == 0) {
            init(100 );
            return;
        }
        loginUser = userRepository.findById(1L).orElse(null);
    }

    @DisplayName("좋아요한 포스트 정보를 가지고온다")
    @Rollback(false)
    @Test
    void test1() {
        // given
        int pageNumber = 1;
        int pageSize = 5;
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);

        // when
        Page<PostResponseDto> likePost = postRepository.findPostByLikeId(loginUser, pageRequest);

        // then
        assertEquals(likePost.getContent().size(), 5);
    }

    @DisplayName("좋아요한 댓글 정보를 가지고온다")
    @Test
    void test2() {
        // given
        int pageNumber = 1;
        int pageSize = 5;
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);

        // when
        Page<CommentResponseDto> likeComment = commentRepository.findCommentByLikeId(loginUser, pageRequest);

        // then
        assertEquals(likeComment.getContent().size(), 5);
    }
}
