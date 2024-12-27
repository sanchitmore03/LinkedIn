package com.san.Linkedin.posts_service.Services;

import com.san.Linkedin.posts_service.Entity.PostLike;
import com.san.Linkedin.posts_service.exception.BadRequestException;
import com.san.Linkedin.posts_service.exception.ResourceNotFoundException;
import com.san.Linkedin.posts_service.repository.PostLikeRepository;
import com.san.Linkedin.posts_service.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostLikesService {

    private final PostLikeRepository postLikeRepository;
    private final PostsRepository postsRepository;

    public void likePost(Long postId,Long userId){
        log.info("Attempting to like post with id: {}",postId);
        boolean exists = postsRepository.existsById(postId);
        if(!exists) throw new ResourceNotFoundException("Post not found with id "+postId);
        boolean alreadyLiked = postLikeRepository.existsByUserIdAndPostId(userId,postId);
        if(alreadyLiked) throw new BadRequestException("Cannot like the post again");

        PostLike postLike = new PostLike();
        postLike.setPostId(postId);
        postLike.setUserId(userId);
        postLikeRepository.save(postLike);

        log.info("Post with id: {} liked successfully",postId);

    }


    public void UnlikePost(Long postId, long userId) {

        log.info("Attempting to like post with id: {}",postId);
        boolean exists = postsRepository.existsById(postId);
        if(!exists) throw new ResourceNotFoundException("Post not found with id "+postId);
        boolean alreadyLiked = postLikeRepository.existsByUserIdAndPostId(userId,postId);
        if(!alreadyLiked) throw new BadRequestException("Cannot Unlike the post which is not liked");

        postLikeRepository.deleteByUserIdAndPostId(userId,postId);

        log.info("Post with id: {} liked successfully",postId);
    }
}
