package com.san.LinkedIn.connections_service.Service;

import com.san.LinkedIn.connections_service.Entity.Person;
import com.san.LinkedIn.connections_service.Repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConnectionsService {
        private final PersonRepository personRepository;
        public List<Person> getFirstDegreeConnections(Long userId){
            log.info("getting first degree connections with user id:{}",userId);

            return personRepository.getFirstDegreeConnections(userId);

        }
}
