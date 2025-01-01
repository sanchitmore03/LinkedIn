package com.san.linkedin.posts_service.repository;


import com.san.linkedin.posts_service.Entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike,Long> {

    boolean existsByUserIdAndPostId(Long userId,Long postId);

    @Transactional
    void deleteByUserIdAndPostId(long userId, Long postId);
}
