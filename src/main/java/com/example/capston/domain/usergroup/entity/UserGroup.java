package com.example.capston.domain.usergroup.entity;


import com.example.capston.domain.group.controller.GroupRequest;
import com.example.capston.domain.group.entity.Club;
import com.example.capston.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_group_id")
    private Long id;

    @Column(name = "calender_setting")
    private boolean calenderSetting = true;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "club_name")
    private String clubName;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "club_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Club club;

    @Enumerated(EnumType.STRING)
    @Column
    private Profile profile;

    public static UserGroup createUserGroup(User user, Club club, Profile profile) {
        UserGroup userGroup = new UserGroup();
        userGroup.user = user;
        userGroup.userEmail = user.getEmail();
        userGroup.club = club;
        userGroup.clubName = club.getClubName();
        userGroup.profile = profile;
        user.addUserGroup(userGroup);
        club.addUserGroup(userGroup);
        return userGroup;
    }

    public void changeSetting(GroupRequest.ChangeSetting changeSetting) {
        this.profile = changeSetting.getProfile();
        this.calenderSetting = changeSetting.isCalendarSetting();
    }

    public void changeProfile(Profile profile) {
        this.profile = profile;
    }
}
