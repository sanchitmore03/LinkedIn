package com.san.linkedin.user_service.DTo;

import lombok.Data;

@Data
public class SignupRequesDto {
    private String name;
    private String email;
    private String password;
}
