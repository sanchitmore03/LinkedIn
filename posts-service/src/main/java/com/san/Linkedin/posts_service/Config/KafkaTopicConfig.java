package com.san.linkedin.posts_service.Config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {
    @Bean
    public NewTopic postCreatedTopic(){
        return new NewTopic("Post-created-topic",3,(short) 1);
    }
    @Bean
    public NewTopic postLikedTopic(){
        return new NewTopic("Post-Liked-topic",3,(short) 1);
    }

}
