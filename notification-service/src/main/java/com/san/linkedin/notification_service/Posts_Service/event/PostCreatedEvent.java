package com.san.linkedin.notification_service.Posts_Service.event;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Builder
@Data
@Getter
@Setter
public class PostCreatedEvent {
    private Long creatorId;
    private String content;
    private Long postId;
}
