package com.example.capston.domain.group.controller;

import com.example.capston.domain.usergroup.entity.Profile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class GroupResponse {


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GroupUserList{
        public Long groupId;
        public String groupName;
        public boolean isOwner;
        public String description;
        List<UserInfo> userinfo;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GroupList{
        Long groupId;
        String GroupName;
        Integer groupCurrentSize;
        Integer groupMaxSize;
        List<String> userNickname;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserInfo{
        public Long userId;
        public Profile profile;
        public String userNickName;
        public Integer greenStamp;
        public Integer yellowStamp;
        public Integer redStamp;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GroupInfo{
        public String groupCode;
        public Profile profile;
        public String groupName;
        public String description;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GroupSetting{
        public Profile profile;
        public boolean calendarSetting;
    }
}
