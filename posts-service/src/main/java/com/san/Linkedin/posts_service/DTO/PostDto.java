package com.san.Linkedin.posts_service.DTO;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
public class PostDto {
    private Long id;


    private String content;


    private Long userId;

    private LocalDateTime creatdAt;

}
