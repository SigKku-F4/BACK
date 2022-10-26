package com.example.capston.domain.group.repository;

import com.example.capston.domain.group.entity.Club;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<Club, Long> {

    Optional<Club> findByClubName(String clubName);

    Optional<Club> findByClubCode(String clubCode);
}
