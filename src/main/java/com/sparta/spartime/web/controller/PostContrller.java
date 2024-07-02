package com.sparta.spartime.web.controller;


import com.sparta.spartime.dto.request.PostRequestDto;
import com.sparta.spartime.dto.response.PostResponseDto;
import com.sparta.spartime.security.principal.UserPrincipal;
import com.sparta.spartime.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostContrller {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponseDto> create(@Valid @RequestBody PostRequestDto requestDto,
                                                  @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(postService.create(requestDto,userPrincipal.getUser()));
    }

    @PostMapping("/anonymous")
    public ResponseEntity<PostResponseDto> createAnonymous(@Valid @RequestBody PostRequestDto requestDto,
                                                           @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(postService.createAnonymous(requestDto,userPrincipal.getUser()));
    }

    @GetMapping
    public ResponseEntity<Page<PostResponseDto>> getPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String type) {
        return ResponseEntity.ok(postService.getPage(page-1,size,type));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDto> get(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.get(postId));
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<PostResponseDto> update(@Valid @RequestBody PostRequestDto requestDto, @PathVariable Long postId,
                                                  @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(postService.update(requestDto,postId,userPrincipal.getUser()));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<?> delete( @PathVariable Long postId,
                                    @AuthenticationPrincipal UserPrincipal userPrincipal) {
        postService.delete(postId,userPrincipal.getUser());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{postId}/like")
    public ResponseEntity<?> like(@PathVariable Long postId,
                                  @AuthenticationPrincipal UserPrincipal userPrincipal) {
        postService.like(postId,userPrincipal.getUser());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{postId}/like")
    public ResponseEntity<?> unlike(@PathVariable Long postId,
                                    @AuthenticationPrincipal UserPrincipal userPrincipal) {
        postService.unlike(postId,userPrincipal.getUser());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/like")
    public ResponseEntity<Page<PostResponseDto>> getLikedPosts(@RequestParam("page") int page,
                                    @AuthenticationPrincipal UserPrincipal userPrincipal) {
        return ResponseEntity.ok(postService.getLikedPosts(page, userPrincipal.getUser()));
    }
}
