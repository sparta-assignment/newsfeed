package com.sparta.spartime.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.spartime.dto.response.PostResponseDto;
import com.sparta.spartime.entity.*;
import com.sparta.spartime.repository.PostCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class PostCustomRepositoryImpl implements PostCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<PostResponseDto> findPostByLikeId(User user, Pageable pageable) {
        QPost post = QPost.post;
        QLike like = QLike.like;

        JPAQuery<PostResponseDto> query = jpaQueryFactory.select(
                Projections.constructor(PostResponseDto.class,
                        post.id,
                        post.title,
                        post.contents,
                        post.user.id,
                        post.likes,
                        post.type,
                        post.user.nickname,
                        post.createdAt,
                        post.updatedAt
                ))
                .from(post)
                .leftJoin(like)
                .on(post.id.eq(like.refId))
                .where(
                        like.user.eq(user),
                        like.referenceType.eq(Like.ReferenceType.POST)
                );

        List<PostResponseDto> posts = query.offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(post.createdAt.desc())
                .fetch();

        return PageableExecutionUtils.getPage(posts, pageable, () -> query.fetch().size());
    }

}
