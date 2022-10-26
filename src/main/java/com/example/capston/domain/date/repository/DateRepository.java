package com.example.capston.domain.date.repository;

import com.example.capston.domain.date.entity.Date;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DateRepository extends JpaRepository<Date, Long> {

    @Query("select d from Date d where d.createdDate>= :start and d.createdDate < :end and d.user.email = :email")
    List<Date> findByEmailAndDatetimeBetween(@Param("email") String email, @Param("start") LocalDate start, @Param("end") LocalDate end);

    @Query("select d from Date d where d.createdDate>= :start and d.createdDate < :end and d.user.id = :userId")
    List<Date> findByIdAndDatetimeBetween(@Param("userId") Long userId, @Param("start") LocalDate start, @Param("end") LocalDate end);

    Optional<Date> findByUserEmailAndCreatedDate(String email, LocalDate localDate);

    Optional<Date> findByCreatedDate(LocalDate createdDate);
}
