package com.san.Linkedin.posts_service.Services;

import com.san.Linkedin.posts_service.DTO.PostCreateRequestDto;
import com.san.Linkedin.posts_service.DTO.PostDto;


import com.san.Linkedin.posts_service.Entity.Post;
import com.san.Linkedin.posts_service.exception.ResourceNotFoundException;
import com.san.Linkedin.posts_service.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostsService {

    private final PostsRepository postsRepository;
    private final ModelMapper modelMapper;

    public PostDto createPost(PostCreateRequestDto postDto, Long userId) {
        Post post = modelMapper.map(postDto, Post.class);
        post.setUserId(userId);

        Post savedPost = postsRepository.save(post);
        return modelMapper.map(savedPost, PostDto.class);
    }

    public PostDto getPostById(Long postId) {
        Post post = postsRepository.findById(postId).orElseThrow(()->
                new ResourceNotFoundException("post not found with id"+postId));
        return modelMapper.map(post, PostDto.class);
    }

    public List<PostDto> getAllPostsOfUser(Long userId) {
        List<Post> post = postsRepository.findByUserId(userId);
        return post.stream().map((element) -> modelMapper.map(element, PostDto.class)).collect(Collectors.toList());
    }
}
