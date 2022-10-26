package com.example.capston.domain.user.controller;

import com.example.capston.aspect.Log;
import com.example.capston.domain.group.controller.GroupController;
import com.example.capston.domain.user.entity.User;
import com.example.capston.domain.user.service.UserService;
import com.example.capston.jwt.argumentresolver.JwtDto;
import com.example.capston.jwt.argumentresolver.LoginUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    @Log
    public ResponseEntity<Response> signup(@RequestBody UserRequest.Signup signup, @LoginUser JwtDto jwtDto){
        userService.join(signup, jwtDto.getEmail());
        return ResponseEntity.ok(new Response(true));
    }

    @GetMapping("/user/{date}")
    @Log
    public List<UserResponse.Calender> getCalender(@PathVariable String date, @LoginUser JwtDto jwtDto){
        return userService.getCalender(date, jwtDto);
    }

    @GetMapping("/user")
    @Log
    public UserResponse.Info getInfo(@LoginUser JwtDto jwtDto){
        User user = userService.getInfo(jwtDto);
        return new UserResponse.Info(user.getNickname(),user.getGender(),user.getAge(),user.getHeight(),user.getWeight());
    }

    @PatchMapping("/user")
    @Log
    public ResponseEntity<Response> changeInfo(@RequestBody UserRequest.changeInfo changeInfo, @LoginUser JwtDto jwtDto){
        userService.changeInfo(changeInfo, jwtDto.getEmail());
        return ResponseEntity.ok(new Response(true));
    }

    @PatchMapping("/user/notification")
    @Log
    public ResponseEntity<Response> changeNotification(@LoginUser JwtDto jwtDto){
        userService.changeNotification(jwtDto.getEmail());
        return ResponseEntity.ok(new Response(true));
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response{
        boolean response;
    }

}
