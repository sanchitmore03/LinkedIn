package com.san.linkedin.connections_service.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class sendConnectionRequestEvent {
    private Long senderId;
    private Long receiverId;
}
