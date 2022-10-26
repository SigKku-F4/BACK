package com.example.capston.domain.user.entity;

import com.example.capston.domain.date.entity.Date;
import com.example.capston.domain.user.controller.UserRequest;
import com.example.capston.domain.usergroup.entity.UserGroup;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "gender")
    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @Column(name = "age")
    private Integer age;

    @Column(name = "height")
    private Integer height;

    @Column(name = "weight")
    private Integer weight;

    @Column(name = "notification")
    @Enumerated(value = EnumType.STRING)
    private Notification notification;

    @Column(name = "need_nutrient")
    private Nutrient needNutrient;

    @OneToMany(mappedBy = "user")
    private List<Date> dateList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<UserGroup> userGroupList = new ArrayList<>();

    @Column(name = "red_stamp_count")
    private Integer redStampCount;

    @Column(name = "yellow_stamp_count")
    private Integer yellowStampCount;

    @Column(name = "green_stamp_count")
    private Integer greenStampCount;

    @Column(name = "confirm")
    private Boolean confirm = false;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public void createUser(String nickname, Integer age, Gender gender
            , Integer height, Integer weight, Notification notification) {
        this.roles = Collections.singletonList("ROLE_USER");
        this.password = null;
        this.nickname = nickname;
        this.age = age;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
        this.notification = notification;
        this.confirm = true;
        this.yellowStampCount = 0;
        this.greenStampCount = 0;
        this.redStampCount = 0;
        this.needNutrient = makeNutrient(gender, age, height, weight);
    }

    public static User tempCreate(String email, String name) {
        User user = new User();
        user.email = email;
        user.name = name;
        return user;
    }

    public void addDate(Date date) {
        this.dateList.add(date);
    }

    private static Nutrient makeNutrient(Gender gender, Integer age, Integer height, Integer weight) {
        return Gender.makeNutrient(gender, age, height, weight);
    }

    public void addUserGroup(UserGroup userGroup) {
        this.userGroupList.add(userGroup);
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public void changeInfo(UserRequest.changeInfo changeInfo) {
        this.nickname = changeInfo.getNickname();
        this.age = changeInfo.getAge();
        this.gender = changeInfo.getGender();
        this.weight = changeInfo.getWeight();
        this.height = changeInfo.getHeight();
    }

    public void changeNotification() {
        if (this.notification.equals(Notification.ON)) {
            this.notification = Notification.OFF;
        } else {
            this.notification = Notification.ON;
        }
    }

    public void addRedStampCount() {
        this.redStampCount++;
    }

    public void addYellowStampCount() {
        this.yellowStampCount++;
    }

    public void addGreenStampCount() {
        this.greenStampCount++;
    }

    public void minusRedStampCount() {
        this.redStampCount--;
    }

    public void minusYellowStampCount() {
        this.yellowStampCount--;
    }

    public void minusGreenStampCount() {
        this.greenStampCount--;
    }
}
