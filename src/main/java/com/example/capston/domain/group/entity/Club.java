package com.example.capston.domain.group.entity;

import com.example.capston.domain.group.controller.GroupRequest;
import com.example.capston.domain.usergroup.entity.UserGroup;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "club_id")
    private Long id;

    @Column(name = "club_code")
    private String clubCode;

    @Column(name = "group_name")
    private String clubName;

    @Column(name = "description")
    private String description;

    @Column(name = "group_owner_email")
    private String clubOwnerEmail;

    @Column(name = "max_size")
    private Integer maxSize;

    @Column(name = "currnet_size")
    private Integer currentSize;

    @OneToMany(mappedBy = "club")
    private List<UserGroup> userGroupList = new ArrayList<>();


    public static Club createGroup(String groupName, String description, String groupOwnerEmail, int maxSize){
        Club club = new Club();
        club.clubName = groupName;
        club.description =description;
        club.clubCode = UUID.randomUUID().toString();
        club.clubOwnerEmail = groupOwnerEmail;
        club.maxSize = maxSize;
        club.currentSize=1;
        return club;
    }

    public void addUserGroup(UserGroup userGroup) {
        this.userGroupList.add(userGroup);
    }

    public void plusCurrentSize(){
        if(maxSize<=currentSize){
            throw new IllegalArgumentException("더이상 유저를 추가할 수 없습니다.");
        }
        currentSize++;
    }

    public void minusCurrentSize(){
        this.currentSize--;
    }

    public void change(GroupRequest.Change change) {
        this.clubName = change.getGroupName();
        this.maxSize = change.getMaxSize();
        this.description = change.getDescription();
    }
}
