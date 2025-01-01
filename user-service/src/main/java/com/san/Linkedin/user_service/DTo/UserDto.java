package com.san.linkedin.user_service.DTo;

import jakarta.persistence.Column;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
public class UserDto {
    private Long id;

     private String email;

     private String password;

     private String name;

    private LocalDateTime localDateTime;
}
