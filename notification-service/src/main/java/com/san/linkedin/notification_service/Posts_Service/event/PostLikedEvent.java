package com.san.linkedin.notification_service.Posts_Service.event;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class PostLikedEvent {
     Long postId;
     Long creatorId;
     Long likedByUserId;
}
