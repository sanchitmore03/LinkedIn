package com.san.linedIn.notification_service.Notification_Service.consumer;


import com.san.linedIn.notification_service.Notification_Service.Clients.ConnectionsClient;
import com.san.linedIn.notification_service.Notification_Service.Dto.PersonDto;
import com.san.linedIn.notification_service.Notification_Service.Entity.Notification;
import com.san.linedIn.notification_service.Notification_Service.Repository.NotificatonRepositroy;
import com.san.linedIn.notification_service.Posts_Service.event.PostCreatedEvent;
import com.san.linedIn.notification_service.Posts_Service.event.PostLikedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostServiceConsumer {
    private final ConnectionsClient connectionsClient;
    private final NotificatonRepositroy notificationRepository;

    @KafkaListener(topics = "post-created-topic")
    public void handlePostCreated(PostCreatedEvent postCreatedEvent){
        List<PersonDto> connections = connectionsClient.getFirstConnection(postCreatedEvent.getCreatorId());
        for(PersonDto connection : connections){
        sendNotification(connection.getUserId(),"your connections"+postCreatedEvent.getCreatorId()
                +"has created a post, Check is out ");
        }
    }
    @KafkaListener(topics = "post-liked-topic")
    public void handlePostLiked(PostLikedEvent postLikedEvent){
        log.info("Sending notifications: handlePostLiked: {}",postLikedEvent);
        String message = String.format("your post has been liked by %d",postLikedEvent.getPostId());
        sendNotification(postLikedEvent.getCreatorId(),message);
    }
    public void sendNotification(Long userId,String message){
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setUserId(userId);

        notificationRepository.save(notification);
    }

}
