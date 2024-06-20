
package com.sparta.spartime.repository;

import com.sparta.spartime.dto.response.PostResponseDto;
import com.sparta.spartime.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<PostResponseDto> findByType(Post.Type type);
}
