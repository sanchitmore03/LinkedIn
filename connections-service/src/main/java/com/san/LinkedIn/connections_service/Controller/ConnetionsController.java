package com.san.LinkedIn.connections_service.Controller;


import com.san.LinkedIn.connections_service.Entity.Person;
import com.san.LinkedIn.connections_service.Service.ConnectionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
public class ConnetionsController {
    private final ConnectionsService connectionsService;

    @GetMapping("/first-degree")
    public ResponseEntity<List<Person>> getFirstConnection(){
        return ResponseEntity.ok(connectionsService.getFirstDegreeConnections());

    }
}
