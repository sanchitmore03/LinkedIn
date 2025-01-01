package com.san.linedIn.notification_service.Notification_Service.Repository;

import com.san.linedIn.notification_service.Notification_Service.Entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificatonRepositroy extends JpaRepository<Notification,Long> {
}
