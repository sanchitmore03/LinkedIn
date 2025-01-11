package com.san.linkedin.notification_service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.errors.SerializationException;
import org.springframework.kafka.listener.ConsumerRecordRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;

public class CustomErrorHandler {

    public DefaultErrorHandler errorHandler() {
        ConsumerRecordRecoverer recoverer = (record, exception) -> {
            if (exception instanceof SerializationException) {
                System.err.println("Serialization failed for record: " + record);
                // Handle the failed record (e.g., send to a dead-letter topic)
            }
        };
        return new DefaultErrorHandler(recoverer);
    }
}
