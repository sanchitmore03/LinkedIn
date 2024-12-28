package com.san.LinkedIn.connections_service.Entity;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node
@Data
public class Person {
    @Id
    @GeneratedValue
    private Long id;

    private Long userId;

    private String name;
}
