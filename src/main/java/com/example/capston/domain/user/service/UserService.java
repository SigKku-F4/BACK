package com.example.capston.domain.user.service;


import com.example.capston.domain.user.controller.UserRequest;
import com.example.capston.domain.user.controller.UserResponse;
import com.example.capston.domain.user.entity.User;
import com.example.capston.jwt.argumentresolver.JwtDto;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService extends UserDetailsService{

    void join(UserRequest.Signup signup, String email);

    List<UserResponse.Calender> getCalender(String date, JwtDto jwtDto);

    void changeInfo(UserRequest.changeInfo changeInfo, String email);

    void changeNotification(String email);

    User getInfo(JwtDto jwtDto);
}
