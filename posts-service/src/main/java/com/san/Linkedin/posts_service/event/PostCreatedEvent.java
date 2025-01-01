package com.san.linkedin.posts_service.event;

import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class PostCreatedEvent {
    private Long creatorId;
    private String content;
    private Long postId;
}
