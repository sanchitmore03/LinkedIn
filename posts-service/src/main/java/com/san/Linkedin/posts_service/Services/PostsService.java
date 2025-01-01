package com.san.linkedin.posts_service.Services;

import com.san.linkedin.posts_service.Clients.ConnectionsClient;
import com.san.linkedin.posts_service.DTO.PersonDto;
import com.san.linkedin.posts_service.DTO.PostCreateRequestDto;
import com.san.linkedin.posts_service.DTO.PostDto;


import com.san.linkedin.posts_service.Entity.Post;
import com.san.linkedin.posts_service.auth.UserContextHolder;
import com.san.linkedin.posts_service.event.PostCreatedEvent;
import com.san.linkedin.posts_service.exception.ResourceNotFoundException;
import com.san.linkedin.posts_service.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostsService {

    private final PostsRepository postsRepository;
    private final ModelMapper modelMapper;
    private final ConnectionsClient connectionsClient;
    private final KafkaTemplate<Long, PostCreatedEvent> kafkaTempale;
    public PostDto createPost(PostCreateRequestDto postDto) {
        Long userId = UserContextHolder.getCurrentUserId();
        Post post = modelMapper.map(postDto, Post.class);
        post.setUserId(userId);
        Post savedPost = postsRepository.save(post);
        PostCreatedEvent postCreatedEvent = PostCreatedEvent.builder()
                .postId(savedPost.getId())
                .creatorId(userId)
                .content(savedPost.getContent())
                .build();
        kafkaTempale.send("post-created-topic",postCreatedEvent);
        return modelMapper.map(savedPost, PostDto.class);
    }

    public PostDto getPostById(Long postId) {
        log.debug("retrieve post with id:{}",postId);
        Long userId = UserContextHolder.getCurrentUserId();
        List<PersonDto> firstConnections = connectionsClient.getFirstConnection();
        // todo : send notifcations to all
        Post post = postsRepository.findById(postId).orElseThrow(()->
                new ResourceNotFoundException("post not found with id"+postId));
        return modelMapper.map(post, PostDto.class);
    }

    public List<PostDto> getAllPostsOfUser(Long userId) {
        List<Post> post = postsRepository.findByUserId(userId);
        return post.stream().map((element) -> modelMapper.map(element, PostDto.class)).collect(Collectors.toList());
    }
}
