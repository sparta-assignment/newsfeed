package com.sparta.spartime;

import com.sparta.spartime.entity.Like;
import com.sparta.spartime.entity.Post;
import com.sparta.spartime.entity.User;
import com.sparta.spartime.helper.ConcurrencyHelper;
import com.sparta.spartime.repository.LikeRepository;
import com.sparta.spartime.repository.PostBatchRepository;
import com.sparta.spartime.repository.UserRepository;
import com.sparta.spartime.security.service.JwtService;
import com.sparta.spartime.service.LikeService;
import com.sparta.spartime.service.PostService;
import com.sparta.spartime.service.SocialService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.when;

public class LikeTest extends SpartimeApplicationTests{
    Long postId = 1L;

    @Autowired
    private PostBatchRepository postBatchRepository;

    @Autowired
    private UserRepository userRepository;

    @SpyBean
    private LikeRepository likeRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private LikeService likeService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private SocialService socialService;

    @BeforeEach
    void setUp() {
    }

    @Rollback(value = false)
    @Transactional
    @Test
    void batchInit() {
        int batchSize = 10000;

        List<User> users = userRepository.findAll();

        if (users.size() == 0) {
            users = createUserData();
            userRepository.saveAll(users);
            userRepository.flush();
        }

        List<Post> posts = createTestPostData(batchSize, users);
        postBatchRepository.batchInsert(posts);
    }

    @DisplayName("좋아요 동시성 테스트")
    @Rollback(value = false)
    @Test
    void test1() throws InterruptedException {
        // given
        likeRepository.deleteAll();
        Post post = Post.builder()
                .id(postId)
                .likes(0L)
                .build();
        postRepository.save(post);
        postRepository.flush();
        User user = userRepository.findById(1L).get();

        // 동시성 체크를 위해 중복 방지 해제
        when(likeRepository.existsByUser_IdAndReferenceTypeAndRefId(user.getId()
                , Like.ReferenceType.POST
                , postId)
        ).thenReturn(false);

        // when
        ConcurrencyHelper.execute(
                () -> postService.like(postId, user)
        );

        likeRepository.flush();

        // then
        int count = likeRepository.countByReferenceTypeAndRefId(Like.ReferenceType.POST, postId);

        // 쓰레드 delay가 없으면 phantom read가 발생?
        assertEquals(count, ConcurrencyHelper.getTHREAD());

        Post resultPost = postRepository.findById(postId).orElse(null);
        System.out.println(resultPost.getLikes());
        assertNotEquals(resultPost.getLikes(), ConcurrencyHelper.getTHREAD());
    }

    @DisplayName("동시성 문제 해결을 위한 update문 실행")
    @Rollback(value = false)
    @Test
    void test2() {
        // given

        // when
        likeService.updateLike();

        // then
        int count = likeRepository.countByReferenceTypeAndRefId(Like.ReferenceType.POST, postId);

        assertEquals(count, ConcurrencyHelper.getTHREAD());

        Post resultPost = postRepository.findById(postId).orElse(null);
        System.out.println(resultPost.getLikes());
        assertEquals(resultPost.getLikes(), ConcurrencyHelper.getTHREAD());
    }
}
