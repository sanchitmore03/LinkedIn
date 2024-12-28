package com.san.LinkedIn.connections_service.Controller;


import com.san.LinkedIn.connections_service.Entity.Person;
import com.san.LinkedIn.connections_service.Service.ConnectionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
public class ConnetionsController {
    private final ConnectionsService connectionsService;

    @GetMapping("/{userId}/first-degree")
    public ResponseEntity<List<Person>> getFirstConnection(@PathVariable Long userId){
        return ResponseEntity.ok(connectionsService.getFirstDegreeConnections(userId));

    }
}
