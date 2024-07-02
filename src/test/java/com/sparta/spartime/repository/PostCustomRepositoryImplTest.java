package com.sparta.spartime.repository;

import com.sparta.spartime.config.FixtureMonkeyUtil;
import com.sparta.spartime.dto.response.CommentResponseDto;
import com.sparta.spartime.dto.response.PostResponseDto;
import com.sparta.spartime.entity.Comment;
import com.sparta.spartime.entity.Like;
import com.sparta.spartime.entity.Post;
import com.sparta.spartime.entity.User;
import net.jqwik.api.Arbitraries;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@ActiveProfiles("test")
@SpringBootTest
class PostCustomRepositoryImplTest {
    private User loginUser;

    @Autowired PostRepository postRepository;
    @Autowired CommentRepository commentRepository;
    @Autowired UserRepository userRepository;
    @Autowired LikeRepository likeRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        if (userRepository.count() == 0) {
            createTestData();
            return;
        }
        loginUser = userRepository.findById(1L).orElse(null);
    }

    List<Post> createTestPostData(List<User> likedUser) {
        return FixtureMonkeyUtil.get()
                .giveMeBuilder(Post.class)
                .setNull("id")
                .set("user", Arbitraries.of(likedUser))
                .sampleList(100);
    }

    List<Like> createTestLikeData(User loginUser) {
        return FixtureMonkeyUtil.get()
                .giveMeBuilder(Like.class)
                .setNull("id")
                .set("user", loginUser)
                .set("refId", Arbitraries.longs().between(1L, 100L))
                .set("referenceType", Arbitraries.of(
                        Like.ReferenceType.POST,
                        Like.ReferenceType.COMMENT
                        ))
                .sampleList(100);
    }

    List<Comment> createTestCommentData(List<Post> posts, List<User> likedUser) {
        return FixtureMonkeyUtil.get()
                .giveMeBuilder(Comment.class)
                .setNull("id")
                .set("user", Arbitraries.of(likedUser))
                .set("post", Arbitraries.of(posts))
                .sampleList(100);
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
        System.out.println(likePost.getContent().get(0).getId());
    }

    @DisplayName("좋아요한 댓글 정보를 가지고온다")
    @Test
    void test2() {
        // given
        int pageNumber = 1;
        int pageSize = 5;
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);

        // when
        Page<CommentResponseDto> likePost = commentRepository.findCommentByLikeId(loginUser, pageRequest);

        // then
        System.out.println(likePost.getContent().get(0).getId());
    }

    void createTestData() {
        // 로그인한 유저 정보
        loginUser = User.builder()
                .id(1L)
                .email("test@gmail.com")
                .password(passwordEncoder.encode("qwer1234!"))
                .status(User.Status.ACTIVITY)
                .nickname("first")
                .intro("im first")
                .role(User.Role.USER)
                .build();

        // 팔로우한 유저 정보
        User likedUser = User.builder()
                .id(2L)
                .email("follow@test.com")
                .password("asdf")
                .role(User.Role.USER)
                .build();

        List<User> users = List.of(loginUser, likedUser);

        userRepository.saveAll(users);

        List<Post> posts = postRepository.saveAll(
                createTestPostData(users)
        );

        likeRepository.saveAll(
                createTestLikeData(loginUser)
        );

        commentRepository.saveAll(
                createTestCommentData(posts, users)
        );

        System.out.println("========================================================== init ==========================================================");
    }
}
