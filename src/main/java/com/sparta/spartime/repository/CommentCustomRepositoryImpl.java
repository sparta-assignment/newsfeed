package com.sparta.spartime.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.spartime.dto.response.CommentResponseDto;
import com.sparta.spartime.dto.response.PostResponseDto;
import com.sparta.spartime.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class CommentCustomRepositoryImpl implements CommentCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<CommentResponseDto> findCommentByLikeId(User user, Pageable pageable) {
        QComment comment = QComment.comment;
        QLike like = QLike.like;

        JPAQuery<CommentResponseDto> query = jpaQueryFactory.select(
                        Projections.constructor(CommentResponseDto.class,
                                comment.id,
                                comment.user.email,
                                comment.contents,
                                comment.createdAt,
                                comment.updatedAt
                        ))
                .from(comment)
                .leftJoin(like)
                .on(comment.id.eq(like.refId))
                .where(
                        like.user.eq(user)
                                .and(like.referenceType.eq(Like.ReferenceType.COMMENT))
                );

        List<CommentResponseDto> posts = query.offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(comment.createdAt.desc())
                .fetch();

        return PageableExecutionUtils.getPage(posts, pageable, () -> query.fetch().size());
    }
}
