package com.san.Linkedin.posts_service.Controller;


import com.san.Linkedin.posts_service.Services.PostLikesService;
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
            postLikesService.likePost(postId,1L);
            return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> UnlikePost(@PathVariable Long postId){
        postLikesService.UnlikePost(postId,1L);
        return ResponseEntity.noContent().build();
    }

}
