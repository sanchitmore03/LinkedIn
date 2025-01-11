package com.san.linkedin.connections_service.Controller;



import com.san.linkedin.connections_service.Entity.Person;
import com.san.linkedin.connections_service.Service.ConnectionsService;
import jakarta.ws.rs.Path;
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

    @PostMapping("/request/{userId}")
    public ResponseEntity<Boolean> sendConnectionRequest(@PathVariable Long userId)
    {
        return ResponseEntity.ok(connectionsService.sendConnectionRequest(userId));
    }
    @PostMapping("/accept/{userId}")
    public ResponseEntity<Boolean> acceptConnectionRequest(@PathVariable Long userId){
        return ResponseEntity.ok(connectionsService.acceptConnectionRequest(userId));
    }
    @PostMapping("/reject/{userId}")
    public ResponseEntity<Boolean> rejectConnectionRequest(@PathVariable Long userId){
        return ResponseEntity.ok(connectionsService.rejectConnectionRequest(userId));
    }
}
