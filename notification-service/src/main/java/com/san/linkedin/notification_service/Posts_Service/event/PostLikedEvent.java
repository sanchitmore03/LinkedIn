package com.san.linkedin.notification_service.Posts_Service.event;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
public class PostLikedEvent {
    private Long postId;
    private Long creatorId;
    private Long likedByUserId;
}
