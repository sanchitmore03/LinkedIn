package com.san.linedIn.notification_service.Notification_Service.Clients;

import com.san.linedIn.notification_service.Notification_Service.Dto.PersonDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "connections-service",path="/connections")
public interface ConnectionsClient {

    @GetMapping("/core/first-degree")
    List<PersonDto> getFirstConnection(Long userId);

}
