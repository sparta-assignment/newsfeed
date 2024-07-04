package com.sparta.spartime.service;

import com.sparta.spartime.entity.Like;
import com.sparta.spartime.entity.User;
import com.sparta.spartime.exception.BusinessException;
import com.sparta.spartime.exception.ErrorCode;
import com.sparta.spartime.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;

    public void like(User user, Like.ReferenceType refType, Long refId) {
        Like like = findLikeBy(user.getId(), refType, refId);
        if (like != null) {
            throw new BusinessException(ErrorCode.LIKE_ALREADY_EXISTS);
        }

        likeRepository.save(
                Like.builder()
                        .refId(refId)
                        .user(user)
                        .referenceType(refType)
                        .build()
        );
    }

    public void unlike(User user, Like.ReferenceType refType, Long refId) {
        Like like = findLikeBy(user.getId(), refType, refId);
        if (like == null) {
            throw new BusinessException(ErrorCode.LIKE_NOT_FOUND);
        }
        likeRepository.delete(like);
    }

    public int countLike(Like.ReferenceType refType, Long refId) {
        return likeRepository.countByReferenceTypeAndRefId(refType, refId);
    }

    @Transactional
    public void updateLike() {
        likeRepository.updatePostLike();
        likeRepository.updateCommentLike();
    }

    private Like findLikeBy(Long userId, Like.ReferenceType refType, Long refId) {
        return likeRepository.findByUserIdAndReferenceTypeAndRefId(userId, refType, refId).orElse(null);
    }
}
