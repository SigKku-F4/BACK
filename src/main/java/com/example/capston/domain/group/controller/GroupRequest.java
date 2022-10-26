package com.example.capston.domain.group.controller;

import com.example.capston.domain.usergroup.entity.Profile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class GroupRequest {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Create{
        private String groupName;
        private String description;
        private Integer maxSize;
        private Profile profile;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Join{
        private String groupCode;
        private Profile profile;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Change{
        private String groupName;
        private Profile profile;
        private String description;
        private Integer maxSize;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ChangeSetting{
        private Profile profile;
        private boolean calendarSetting;
    }

}
