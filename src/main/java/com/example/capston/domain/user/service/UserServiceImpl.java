package com.example.capston.domain.user.service;

import com.example.capston.aspect.Log;
import com.example.capston.domain.date.repository.DateRepository;
import com.example.capston.domain.group.repository.GroupRepository;
import com.example.capston.domain.user.controller.UserRequest;
import com.example.capston.domain.user.controller.UserResponse;
import com.example.capston.domain.user.entity.User;
import com.example.capston.domain.user.repository.UserRepository;
import com.example.capston.domain.usergroup.repository.UserGroupRepository;
import com.example.capston.jwt.JwtProvider;
import com.example.capston.jwt.argumentresolver.JwtDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final JwtProvider jwtProvider;
    private final DateRepository dateRepository;
    private final UserGroupRepository userGroupRepository;
    private final GroupRepository groupRepository;

    @Override
    @Log
    public void join(UserRequest.Signup signup, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    throw new IllegalArgumentException("없는 아이디 입니다.");
                });

        user.createUser(signup.getNickname(), signup.getAge(), signup.getGender(),
                signup.getHeight(), signup.getWeight(), signup.getNotification());
    }

    @Override
    @Log
    public List<UserResponse.Calender> getCalender(String date, JwtDto jwtDto) {
        String[] s = date.split("-");
        LocalDate start = LocalDate.of(Integer.parseInt(s[0]), Integer.parseInt(s[1]), 1);
        LocalDate end = start.plusMonths(1);
        return dateRepository.findByEmailAndDatetimeBetween(jwtDto.getEmail(), start, end)
                .stream()
                .map(item -> new UserResponse.Calender(item.getId(), item.getCreatedDate(), item.getStamp()))
                .collect(Collectors.toList());
    }

    @Override
    @Log
    public void changeInfo(UserRequest.changeInfo changeInfo, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("없는 유저 이메일"));

        user.changeInfo(changeInfo);
    }

    @Override
    @Log
    public void changeNotification(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("없는 유저 이메일"));

        user.changeNotification();
    }

    @Override
    public User getInfo(JwtDto jwtDto) {
        User user = userRepository.findByEmail(jwtDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("없는 유저 이메일"));

        return user;
    }

    @Override
    @Log
    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 아이디입니다. "));
    }
}
