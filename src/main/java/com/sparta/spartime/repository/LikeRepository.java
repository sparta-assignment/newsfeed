
package com.sparta.spartime.repository;

import com.sparta.spartime.dto.CountLikeDto;
import com.sparta.spartime.entity.Like;
import com.sparta.spartime.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    boolean existsByUser_IdAndReferenceTypeAndRefId(Long userId, Like.ReferenceType referenceType, Long refId);
    Optional<Like> findByUserIdAndReferenceTypeAndRefId(Long userId, Like.ReferenceType referenceType, Long referenceId);

    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true,
    value = "UPDATE posts p\n" +
            "    JOIN (\n" +
            "        SELECT ref_id, COUNT(*) AS like_count\n" +
            "        FROM likes\n" +
            "        WHERE reference_type = 'POST'\n" +
            "        GROUP BY ref_id\n" +
            "    ) l ON p.post_id = l.ref_id\n" +
            "SET p.likes = l.like_count;")
    void updatePostLike();

    @Modifying(clearAutomatically = true)
    @Query(nativeQuery = true,
            value = "UPDATE comments p\n" +
                    "    JOIN (\n" +
                    "        SELECT ref_id, COUNT(*) AS like_count\n" +
                    "        FROM likes\n" +
                    "        WHERE reference_type = 'COMMENT'\n" +
                    "        GROUP BY ref_id\n" +
                    "    ) l ON p.comment_id = l.ref_id\n" +
                    "SET p.likes = l.like_count;")
    void updateCommentLike();

    int countByReferenceTypeAndRefId(Like.ReferenceType referenceType, Long refId);


    @Query(value = "SELECT\n" +
            "    SUM(CASE WHEN reference_type = 'POST' THEN 1 ELSE 0 END) AS postLikeCount,\n" +
            "    SUM(CASE WHEN reference_type = 'COMMENT' THEN 1 ELSE 0 END) AS commentLikeCount\n" +
            "FROM likes\n" +
            "WHERE user_id = :userId;"
            , nativeQuery = true)
    CountLikeDto countLikeByUser(@Param("userId") Long userId);
}
