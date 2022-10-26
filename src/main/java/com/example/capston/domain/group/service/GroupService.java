package com.example.capston.domain.group.service;

import com.example.capston.domain.date.controller.DateResponse;
import com.example.capston.domain.group.controller.GroupRequest;
import com.example.capston.domain.group.controller.GroupResponse;
import com.example.capston.domain.user.controller.UserResponse;
import com.example.capston.jwt.argumentresolver.JwtDto;

import java.time.LocalDate;
import java.util.List;

public interface GroupService {
    Long create(GroupRequest.Create createRequest, String email);

    Long join(GroupRequest.Join joinRequest, String email);

    GroupResponse.GroupUserList getGroupInfo(Long groupId, JwtDto jwtDto);

    void changeNotification(Long groupId, String email, GroupRequest.ChangeSetting changeSetting);

    void withdrawal(Long groupId, String email);

    void withdrawalGroup(Long groupId, String email);

    void change(GroupRequest.Change change, JwtDto jwtDto, Long groupId);

    String getGroupCode(Long groupId);

    List<GroupResponse.GroupList> getGroupList(String email);

    GroupResponse.GroupInfo getGroupSetting(Long groupId, JwtDto jwtDto);

    List<UserResponse.Calender> getUserGroupCalender(Long userId, String date);

    GroupResponse.GroupSetting getUserGroupSetting(Long groupId, JwtDto jwtDto);

    DateResponse.Calender getCalendar(Long userId, LocalDate date, Long groupId);
}
