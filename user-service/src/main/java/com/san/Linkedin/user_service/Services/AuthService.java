package com.san.linkedin.user_service.Services;


import com.san.linkedin.user_service.DTo.LoginRequesDto;
import com.san.linkedin.user_service.DTo.SignupRequesDto;
import com.san.linkedin.user_service.DTo.UserDto;
import com.san.linkedin.user_service.Entity.User;
import com.san.linkedin.user_service.Repos.UserRepository;
import com.san.linkedin.user_service.exception.BadRequestException;
import com.san.linkedin.user_service.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final com.san.linkedin.user_service.utils.JwtService jwtService;

    public UserDto signUp(SignupRequesDto signupRequestDto) {
        boolean exists = userRepository.existsByEmail(signupRequestDto.getEmail());
        if(exists) {
            throw new BadRequestException("User already exists, cannot signup again.");
        }

        User user = modelMapper.map(signupRequestDto, User.class);
        user.setPassword(com.san.linkedin.user_service.utils.PasswordUtil.hashPassword(signupRequestDto.getPassword()));

        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }

    public String login(LoginRequesDto loginRequesDto) {
        User user = userRepository.findByEmail(loginRequesDto.getEmail()).orElseThrow(()->
                new ResourceNotFoundException("User nto found with emai"+loginRequesDto.getEmail()));
        boolean isPasswordMatch = com.san.linkedin.user_service.utils.PasswordUtil.checkPassword(loginRequesDto.getPassword(),user.getPassword());
        if(!isPasswordMatch){
            throw new BadRequestException("Incorrect Passwrod");
        }
        return jwtService.generateAccessToken(user);

    }
}
