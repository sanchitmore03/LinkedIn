package com.san.linkedin.posts_service.Controller;


import com.san.linkedin.posts_service.DTO.PostCreateRequestDto;
import com.san.linkedin.posts_service.DTO.PostDto;

import com.san.linkedin.posts_service.Services.PostsService;
import com.san.linkedin.posts_service.auth.UserContextHolder;
import com.san.linkedin.posts_service.event.PostCreatedEvent;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
public class PostsController {

    private final PostsService postsService;


    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostCreateRequestDto postDto) {
        PostDto createdPost = postsService.createPost(postDto);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPost(@PathVariable Long postId) {
        Long userId = UserContextHolder.getCurrentUserId();
        PostDto postDto = postsService.getPostById(postId);
        return ResponseEntity.ok(postDto);
    }
//
    @GetMapping("/users/{userId}/allPosts")
    public ResponseEntity<List<PostDto>> getAllPostsOfUser(@PathVariable Long userId) {
        List<PostDto> posts = postsService.getAllPostsOfUser(userId);
        return ResponseEntity.ok(posts);
    }

}
