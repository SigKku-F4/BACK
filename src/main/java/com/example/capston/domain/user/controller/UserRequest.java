package com.example.capston.domain.user.controller;

import com.example.capston.domain.date.entity.Date;
import com.example.capston.domain.user.entity.Gender;
import com.example.capston.domain.user.entity.Notification;
import com.example.capston.domain.user.entity.Nutrient;
import com.example.capston.domain.user.entity.Stamp;
import com.example.capston.domain.usergroup.entity.UserGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class UserRequest {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Signup{

        private String nickname;

        private Gender gender;

        private Integer age;

        private Integer height;

        private Integer weight;

        private Notification notification;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class changeInfo{

        private String nickname;

        private Gender gender;

        private Integer age;

        private Integer height;

        private Integer weight;

    }


}
