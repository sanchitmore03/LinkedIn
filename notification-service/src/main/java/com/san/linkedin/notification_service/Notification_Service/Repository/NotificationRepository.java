package com.san.linkedin.notification_service.Notification_Service.Repository;


import com.san.linkedin.notification_service.Notification_Service.Entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;



public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
