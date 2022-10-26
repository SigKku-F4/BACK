package com.example.capston.domain.usergroup.repository;

import com.example.capston.domain.group.entity.Club;
import com.example.capston.domain.user.entity.User;
import com.example.capston.domain.usergroup.entity.UserGroup;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;
import java.util.Optional;

public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {

    Optional<UserGroup> findByClubAndUser(Club club, User user);

    Long deleteByClubAndUser(Club club, User user);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    Long deleteByClub(Club club);

    @EntityGraph(attributePaths = "club")
    List<UserGroup> findByUserEmail(String userEmail);

    @EntityGraph(attributePaths = "user")
    List<UserGroup> findByClub(Club club);
}
