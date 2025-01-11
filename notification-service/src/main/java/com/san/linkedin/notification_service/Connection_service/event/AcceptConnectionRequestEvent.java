package com.san.linkedin.notification_service.Connection_service.event;



import lombok.Data;

@Data
public class AcceptConnectionRequestEvent {
    private Long senderId;
    private Long receiverId;
}
