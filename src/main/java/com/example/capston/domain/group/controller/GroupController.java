package com.example.capston.domain.group.controller;

import com.example.capston.aspect.Log;
import com.example.capston.domain.date.controller.DateResponse;
import com.example.capston.domain.date.service.DateService;
import com.example.capston.domain.group.service.GroupService;
import com.example.capston.domain.user.controller.UserResponse;
import com.example.capston.jwt.argumentresolver.JwtDto;
import com.example.capston.jwt.argumentresolver.LoginUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/groups")
public class GroupController {

    private final GroupService groupService;


    @PostMapping
    @Log
    public ResponseEntity<GroupIdResponse> createGroup(@RequestBody GroupRequest.Create createRequest, @LoginUser JwtDto jwtDto){
        Long groupId = groupService.create(createRequest, jwtDto.getEmail());
        return ResponseEntity.ok(new GroupIdResponse(groupId));
    }

    @PostMapping("/user")
    @Log
    public ResponseEntity<GroupIdResponse> joinGroup(@RequestBody GroupRequest.Join joinRequest, @LoginUser JwtDto jwtDto){
        Long groupId = groupService.join(joinRequest, jwtDto.getEmail());
        return ResponseEntity.ok(new GroupIdResponse(groupId));
    }

    @GetMapping("/{groupId}")
    @Log
    public GroupResponse.GroupUserList getGroupUserList(@PathVariable Long groupId, @LoginUser JwtDto jwtDto){
        return groupService.getGroupInfo(groupId, jwtDto);
    }

    @GetMapping
    @Log
    public List<GroupResponse.GroupList> getGroupList(@LoginUser JwtDto jwtDto){
        return groupService.getGroupList(jwtDto.getEmail());
    }

    @GetMapping("/user/calendar/{userId}")
    @Log
    public List<UserResponse.Calender> getUserGroupCalender(@PathVariable Long userId,
                                                            @DateTimeFormat(pattern = "yyyy-MM")@RequestParam String date){
        return groupService.getUserGroupCalender(userId, date);
    }

    @GetMapping("/user/calendar/detail/{userId}")
    @Log
    public DateResponse.Calender getUserGroupCalendarDetail(@PathVariable Long userId,
                                                            @RequestParam Long groupId,
                                                            @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam LocalDate date){
        return groupService.getCalendar(userId, date, groupId);
    }

    @GetMapping("/user-group/{groupId}")
    @Log
    public GroupResponse.GroupSetting getUserGroupSetting(@PathVariable Long groupId, @LoginUser JwtDto jwtDto){
        return groupService.getUserGroupSetting(groupId, jwtDto);
    }

    @PatchMapping("/user-group/{groupId}")
    @Log
    public ResponseEntity<Response> changeNotification(@PathVariable Long groupId,
                                                       @RequestBody GroupRequest.ChangeSetting changeSetting,
                                                       @LoginUser JwtDto jwtDto){
        groupService.changeNotification(groupId, jwtDto.getEmail(), changeSetting);

        return ResponseEntity.ok(new Response(true));
    }

    @DeleteMapping("/user-group/{groupId}")
    @Log
    public ResponseEntity<Response> withdrawalGroup(@PathVariable Long groupId, @LoginUser JwtDto jwtDto){
        groupService.withdrawal(groupId, jwtDto.getEmail());

        return ResponseEntity.ok(new Response(true));
    }

    @DeleteMapping("/admin/{groupId}")
    @Log
    public ResponseEntity<Response> deleteGroup(@PathVariable Long groupId, @LoginUser JwtDto jwtDto){
        groupService.withdrawalGroup(groupId, jwtDto.getEmail());

        return ResponseEntity.ok(new Response(true));
    }

    @PatchMapping("/admin/settings/{groupId}")
    @Log
    public ResponseEntity<Response> changeNameAndDescription(@PathVariable Long groupId,
                                                   @RequestBody GroupRequest.Change change,
                                                   @LoginUser JwtDto jwtDto){
        groupService.change(change, jwtDto, groupId);

        return ResponseEntity.ok(new Response(true));
    }

    @GetMapping("/admin/settings/{groupId}")
    @Log
    public GroupResponse.GroupInfo getGroupSetting(@PathVariable Long groupId, @LoginUser JwtDto jwtDto){
        return groupService.getGroupSetting(groupId, jwtDto);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response{
        boolean response;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GroupIdResponse{
        Long groupId;
    }
}
