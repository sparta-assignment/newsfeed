package com.sparta.spartime;

import com.sparta.spartime.config.FixtureMonkeyUtil;
import com.sparta.spartime.entity.Comment;
import com.sparta.spartime.entity.Like;
import com.sparta.spartime.entity.Post;
import com.sparta.spartime.entity.User;
import com.sparta.spartime.repository.CommentRepository;
import com.sparta.spartime.repository.LikeRepository;
import com.sparta.spartime.repository.PostRepository;
import com.sparta.spartime.repository.UserRepository;
import net.jqwik.api.Arbitraries;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@ActiveProfiles("test")
@SpringBootTest
public class SpartimeApplicationTests {
    @Autowired
    PostRepository postRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    LikeRepository likeRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    public void init(int i) {
        List<User> users = createUserData();
        if (userRepository.count() == 0) {
            userRepository.saveAll(users);
            createTestData(i, users);
        }
    }

    public void init(int i, List<User> users) {
        if (userRepository.count() == 0) {
            userRepository.saveAll(users);
            createTestData(i, users);
        }
    }

    void createTestData(int i, List<User> users) {
        List<Post> posts = postRepository.saveAll(
                createTestPostData(i, users)
        );

        likeRepository.saveAll(
                createTestLikeData(i, users.get(0))
        );

        commentRepository.saveAll(
                createTestCommentData(i, posts, users)
        );

        System.out.println("========================================================== init ==========================================================");
    }

    public List<User> createUserData() {
        // 로그인한 유저 정보
        User loginUser = User.builder()
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
                .password(passwordEncoder.encode("'asdf"))
                .role(User.Role.USER)
                .build();

        return List.of(loginUser, likedUser);
    }

    public List<Post> createTestPostData(int i, List<User> likedUser) {
        return FixtureMonkeyUtil.get()
                .giveMeBuilder(Post.class)
                .setNull("id")
                .set("user", Arbitraries.of(likedUser))
                .set("type", Arbitraries.of(Post.Type.values()))
                .set("likes", 0L)
                .sampleList(i);
    }

    public List<Like> createTestLikeData(int i, User loginUser) {
        return FixtureMonkeyUtil.get()
                .giveMeBuilder(Like.class)
                .setNull("id")
                .set("user", loginUser)
                .set("refId", Arbitraries.longs().between(1L, 100L))
                .set("referenceType", Arbitraries.of(
                        Like.ReferenceType.POST,
                        Like.ReferenceType.COMMENT
                ))
                .sampleList(i);
    }

    public List<Comment> createTestCommentData(int i, List<Post> posts, List<User> likedUser) {
        return FixtureMonkeyUtil.get()
                .giveMeBuilder(Comment.class)
                .setNull("id")
                .set("user", Arbitraries.of(likedUser))
                .set("post", Arbitraries.of(posts))
                .setNull("likes")
                .sampleList(i);
    }
}
