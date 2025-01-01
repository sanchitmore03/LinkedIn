package com.san.linkedin.user_service.config;

import com.san.linkedin.user_service.DTo.LoginRequesDto;
import com.san.linkedin.user_service.DTo.SignupRequesDto;

import com.san.linkedin.user_service.DTo.UserDto;
import com.san.linkedin.user_service.Services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;


    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody SignupRequesDto signupRequesDto){
        UserDto userDto = authService.signUp(signupRequesDto);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login (@RequestBody LoginRequesDto loginRequesDto){
        String token = authService.login(loginRequesDto);
        return ResponseEntity.ok(token);
    }



}
