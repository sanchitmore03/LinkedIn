package com.san.linkedin.connections_service.Service;

import com.san.linkedin.connections_service.Entity.Person;
import com.san.linkedin.connections_service.Repository.PersonRepository;
import com.san.linkedin.connections_service.auth.UserContextHolder;
import com.san.linkedin.connections_service.event.AcceptConnectionRequestEvent;
import com.san.linkedin.connections_service.event.sendConnectionRequestEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.task.ThreadPoolTaskExecutorBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConnectionsService {
        private final PersonRepository personRepository;
        private final KafkaTemplate<Long , sendConnectionRequestEvent> sendRequestKafaTemplate;
    private final KafkaTemplate<Long , AcceptConnectionRequestEvent> acceptRequestKafaTemplate;
    private final NewTopic acceptConnectionRequestTopic;

    public List<Person> getFirstDegreeConnections(){
            Long userId = UserContextHolder.getCurrentUserId();
            log.info("getting first degree connections with user id:{}",userId);

            return personRepository.getFirstDegreeConnections(userId);

        }

    public Boolean sendConnectionRequest(Long receiverId) {
            Long senderId = UserContextHolder.getCurrentUserId();
            log.info("Trying to send connection request,sender: {},reciever:{}",senderId,receiverId);
            if(senderId == receiverId){
                throw new RuntimeException("Both sender and receiver are the same");
            }
            boolean alreadtSentReques = personRepository.connectionRequestExists(senderId,receiverId);
            if(alreadtSentReques){
                throw new RuntimeException("connection request already exits");
            }
            boolean alreadyConnected = personRepository.alreadyConnected(senderId,receiverId);
            if(alreadyConnected){
                throw new RuntimeException("user is already conected");
            }
            log.info("Succefully send the connection request");
            personRepository.addConnectionRequest(senderId,receiverId);
            sendConnectionRequestEvent sendConnectionRequestEvent = com.san.linkedin.connections_service.event.sendConnectionRequestEvent.builder()
                    .senderId(senderId)
                    .receiverId(receiverId)
                    .build();
            sendRequestKafaTemplate.send("send-connection-request-topic",sendConnectionRequestEvent);
            return true;

    }

    public Boolean acceptConnectionRequest(Long senderId) {
        Long receiverId = UserContextHolder.getCurrentUserId();
        boolean connectionRequestExists = personRepository.connectionRequestExists(senderId,receiverId);
        if(!connectionRequestExists){
            throw new RuntimeException("connection request doesnot exits");
        }
        personRepository.acceptConnectionRequest(senderId,receiverId);
        log.info("succefully accepted the connection request,sender:{}, receiver:{}",senderId,receiverId);

        AcceptConnectionRequestEvent acceptConnectionRequestEvent = AcceptConnectionRequestEvent.builder()
                .senderId(senderId)
                .receiverId(receiverId)
                .build();
        acceptRequestKafaTemplate.send("send-connection-request-topic",acceptConnectionRequestEvent);
        return true;

    }

    public Boolean rejectConnectionRequest(Long senderId) {
        Long receiverId = UserContextHolder.getCurrentUserId();
        boolean connectionRequestExists = personRepository.connectionRequestExists(senderId,receiverId);
        if(!connectionRequestExists){
            throw new RuntimeException("connection request doesnot exits,Cannot delete");
        }
        personRepository.rejectConnectionRequest(senderId,receiverId);
        return true;
    }
}
