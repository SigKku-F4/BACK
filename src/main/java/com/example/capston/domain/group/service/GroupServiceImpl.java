package com.example.capston.domain.group.service;

import com.example.capston.aspect.Log;
import com.example.capston.domain.date.controller.DateResponse;
import com.example.capston.domain.date.entity.Date;
import com.example.capston.domain.date.repository.DateRepository;
import com.example.capston.domain.group.controller.GroupRequest;
import com.example.capston.domain.group.controller.GroupResponse;
import com.example.capston.domain.group.entity.Club;
import com.example.capston.domain.group.repository.GroupRepository;
import com.example.capston.domain.user.controller.UserResponse;
import com.example.capston.domain.user.entity.User;
import com.example.capston.domain.user.repository.UserRepository;
import com.example.capston.domain.usergroup.entity.UserGroup;
import com.example.capston.domain.usergroup.repository.UserGroupRepository;
import com.example.capston.jwt.argumentresolver.JwtDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class GroupServiceImpl implements GroupService {
    private final UserRepository userRepository;

    private final GroupRepository groupRepository;

    private final UserGroupRepository userGroupRepository;

    private final DateRepository dateRepository;
    @Override
    @Log
    public Long create(GroupRequest.Create createRequest, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("없는 이메일입니다."));

        Club club = Club.createGroup(createRequest.getGroupName(), createRequest.getDescription(), email,
                createRequest.getMaxSize());

        groupRepository.save(club);

        UserGroup userGroup = UserGroup.createUserGroup(user, club, createRequest.getProfile());

        return userGroupRepository.save(userGroup).getId();
    }

    @Override
    @Log
    public Long join(GroupRequest.Join joinRequest, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("없는 이메일입니다."));

        Club club = groupRepository.findByClubCode(joinRequest.getGroupCode())
                .orElseThrow(() -> new IllegalArgumentException("없는 그룹 코드입니다."));

        userGroupRepository.findByClubAndUser(club, user)
                .ifPresent(
                        a -> {
                            throw new IllegalArgumentException("이미 가입한 유저");
                        }
                );

        UserGroup userGroup = UserGroup.createUserGroup(user, club, joinRequest.getProfile());

        userGroupRepository.save(userGroup);

        club.plusCurrentSize();

        return club.getId();
    }

    @Override
    @Log
    public GroupResponse.GroupUserList getGroupInfo(Long groupId, JwtDto jwtDto) {
        Club club = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 그룹 아이디입니다."));

        List<UserGroup> userGroupList = club.getUserGroupList();

        List<GroupResponse.UserInfo> userinfo = new ArrayList<>();

        userGroupList.stream()
                .forEach(userGroup -> {
                    User user = userGroup.getUser();
                    userinfo.add(new GroupResponse.UserInfo(user.getId(),userGroup.getProfile(),
                            user.getNickname(),user.getGreenStampCount(),user.getYellowStampCount(),user.getRedStampCount()));
                });

        return new GroupResponse.GroupUserList(club.getId(),club.getClubName(), (jwtDto.getEmail().equals(club.getClubOwnerEmail())), club.getDescription(), userinfo);
    }

    @Override
    @Log
    public void change(GroupRequest.Change change, JwtDto jwtDto, Long groupId) {
        Club club = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 그룹 코드입니다."));

        User user = userRepository.findByEmail(jwtDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("없는 이메일입니다."));

        UserGroup userGroup = userGroupRepository.findByClubAndUser(club, user)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 요청"));

        userGroup.changeProfile(change.getProfile());

        club.change(change);
    }

    @Override
    @Log
    public String getGroupCode(Long groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("없는 그룹 아이디")).getClubCode();
    }

    @Override
    @Log
    public List<GroupResponse.GroupList> getGroupList(String email) {
        List<UserGroup> groupList = userGroupRepository.findByUserEmail(email);

        return groupList.stream()
                .map(userGroup -> {
                    List<String> nickNames = userGroupRepository.findByClub(userGroup.getClub())
                            .stream()
                            .map(userGroup1 -> new String(userGroup1.getUser().getNickname()))
                            .collect(Collectors.toList());
                    GroupResponse.GroupList response = new GroupResponse.GroupList();
                    response.setGroupName(userGroup.getClub().getClubName());
                    response.setGroupId(userGroup.getClub().getId());
                    response.setGroupCurrentSize(userGroup.getClub().getCurrentSize());
                    response.setGroupMaxSize(userGroup.getClub().getMaxSize());
                    response.setUserNickname(nickNames);
                    return response;
                }).collect(Collectors.toList());
    }

    @Override
    @Log
    public GroupResponse.GroupInfo getGroupSetting(Long groupId, JwtDto jwtDto) {
        Club club = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 그룹 코드입니다."));

        User user = userRepository.findByEmail(jwtDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("없는 이메일입니다."));

        UserGroup userGroup = userGroupRepository.findByClubAndUser(club, user)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 요청"));


        return new GroupResponse.GroupInfo(club.getClubCode(), userGroup.getProfile(), club.getClubName(), club.getDescription());
    }

    @Override
    @Log
    public List<UserResponse.Calender> getUserGroupCalender(Long userId, String date) {
        String[] s = date.split("-");
        LocalDate start = LocalDate.of(Integer.parseInt(s[0]), Integer.parseInt(s[1]), 1);
        LocalDate end = start.plusMonths(1);

        return dateRepository.findByIdAndDatetimeBetween(userId, start, end)
                .stream()
                .map(item -> new UserResponse.Calender(item.getId(), item.getCreatedDate(), item.getStamp()))
                .collect(Collectors.toList());
    }

    @Override
    public GroupResponse.GroupSetting getUserGroupSetting(Long groupId, JwtDto jwtDto) {
        Club club = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 그룹 코드입니다."));

        User user = userRepository.findByEmail(jwtDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("없는 이메일입니다."));

        UserGroup userGroup = userGroupRepository.findByClubAndUser(club, user)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 요청"));

        return new GroupResponse.GroupSetting(userGroup.getProfile(), userGroup.isCalenderSetting());
    }

    @Override
    public DateResponse.Calender getCalendar(Long userId, LocalDate date, Long groupId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("없는 아이디"));

        Club club = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("없는 그룹"));

        Date current = dateRepository.findByUserEmailAndCreatedDate(user.getEmail(), date)
                .orElse(Date.createDate(date, user));

        UserGroup userGroup = userGroupRepository.findByClubAndUser(club, user)
                .orElseThrow(() -> new IllegalArgumentException("가입 되지 않은 그룹"));

        return DateResponse.Calender.of(current, user.getNeedNutrient());
    }

    @Override
    @Log
    public void changeNotification(Long groupId, String email, GroupRequest.ChangeSetting changeSetting) {

        Club club = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("없는 그룹 아이디"));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("없는 유저 이메일"));

        UserGroup userGroup = userGroupRepository.findByClubAndUser(club, user)
                .orElseThrow(() -> new IllegalArgumentException("가입 되지 않은 그룹"));

        userGroup.changeSetting(changeSetting);
    }

    @Override
    @Log
    public void withdrawal(Long groupId, String email) {
        Club club = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("없는 그룹 아이디"));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("없는 유저 이메일"));

        userGroupRepository.deleteByClubAndUser(club, user);

        club.minusCurrentSize();
    }

    @Override
    @Log
    public void withdrawalGroup(Long groupId, String email) {
        Club club = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("없는 그룹 아이디"));

        userGroupRepository.deleteByClub(club);

        groupRepository.delete(club);
    }

}
