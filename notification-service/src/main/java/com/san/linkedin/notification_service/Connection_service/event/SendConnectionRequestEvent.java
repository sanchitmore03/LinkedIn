package com.san.linkedin.notification_service.Connection_service.event;


import lombok.Data;

@Data
public class SendConnectionRequestEvent {
    private Long senderId;
    private Long receiverId;
}
