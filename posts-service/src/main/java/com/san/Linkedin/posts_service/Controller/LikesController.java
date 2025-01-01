package com.san.linkedin.posts_service.Controller;


import com.san.linkedin.posts_service.Services.PostLikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikesController {

    private final PostLikesService postLikesService;

    @PostMapping("/{postId}")
    public ResponseEntity<Void> likePost(@PathVariable Long postId){
            postLikesService.likePost(postId);
            return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> UnlikePost(@PathVariable Long postId){
        postLikesService.UnlikePost(postId);
        return ResponseEntity.noContent().build();
    }

}
