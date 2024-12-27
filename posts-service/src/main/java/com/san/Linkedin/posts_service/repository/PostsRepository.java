package com.san.Linkedin.posts_service.repository;

import com.san.Linkedin.posts_service.Entity.Post;
import org.springframework.boot.autoconfigure.data.ConditionalOnRepositoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostsRepository extends JpaRepository<Post,Long> {
    List<Post> findByUserId(Long userId);
}
