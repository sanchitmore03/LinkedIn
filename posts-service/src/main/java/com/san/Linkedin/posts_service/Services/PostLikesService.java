package com.san.linkedin.posts_service.Services;

import com.san.linkedin.posts_service.Entity.Post;
import com.san.linkedin.posts_service.Entity.PostLike;
import com.san.linkedin.posts_service.auth.UserContextHolder;
import com.san.linkedin.posts_service.event.PostLikedEvent;
import com.san.linkedin.posts_service.exception.BadRequestException;
import com.san.linkedin.posts_service.exception.ResourceNotFoundException;
import com.san.linkedin.posts_service.repository.PostLikeRepository;
import com.san.linkedin.posts_service.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class PostLikesService {

    private final PostLikeRepository postLikeRepository;
    private final PostsRepository postsRepository;
    private final KafkaTemplate<Long,PostLikedEvent> kafkaTemplate;

    public void likePost(Long postId){
        Long userId = UserContextHolder.getCurrentUserId();
        log.info("Attempting to like post with id: {}",postId);
        Post post = postsRepository.findById(postId).orElseThrow(()->
                new ResourceNotFoundException("Post not found with id "+postId));

        boolean alreadyLiked = postLikeRepository.existsByUserIdAndPostId(userId,postId);
        if(alreadyLiked) throw new BadRequestException("Cannot like the post again");

        PostLike postLike = new PostLike();
        postLike.setPostId(postId);
        postLike.setUserId(userId);
        postLikeRepository.save(postLike);

        log.info("Post with id: {} liked successfully",postId);

        PostLikedEvent postLikedEvent = PostLikedEvent.builder()
                        .postId(postId)
                                .likedByUserId(userId)
                .creatorId(post.getUserId()).build();
        kafkaTemplate.send("post-liked-topic",postId,postLikedEvent);
    }


    public void UnlikePost(Long postId) {
        Long userId = UserContextHolder.getCurrentUserId();
        log.info("Attempting to like post with id: {}",postId);
        boolean exists = postsRepository.existsById(postId);
        if(!exists) throw new ResourceNotFoundException("Post not found with id "+postId);
        boolean alreadyLiked = postLikeRepository.existsByUserIdAndPostId(userId,postId);
        if(!alreadyLiked) throw new BadRequestException("Cannot Unlike the post which is not liked");

        postLikeRepository.deleteByUserIdAndPostId(userId,postId);

        log.info("Post with id: {} liked successfully",postId);
    }
}
