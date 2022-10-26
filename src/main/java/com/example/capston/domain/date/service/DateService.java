package com.example.capston.domain.date.service;

import com.example.capston.domain.date.controller.DateRequest;
import com.example.capston.domain.date.controller.DateResponse;
import com.example.capston.jwt.argumentresolver.JwtDto;

import java.time.LocalDate;

public interface DateService {
    DateResponse.Calender getCalender(LocalDate date, String email);

    void addCalender(LocalDate date, JwtDto jwtDto, DateRequest.AddCalender request);

    void patchCalendar(LocalDate date, JwtDto jwtDto, DateRequest.PatchCalender request);

}
