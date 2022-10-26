package com.example.capston.domain.user.controller;

import com.example.capston.domain.user.entity.Gender;
import com.example.capston.domain.user.entity.Notification;
import com.example.capston.domain.user.entity.Stamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserResponse {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Calender{
        public Long dateId;
        public LocalDate dateTime;
        public Stamp stamp;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Info{
        private String nickname;

        private Gender gender;

        private Integer age;

        private Integer height;

        private Integer weight;

    }

}
