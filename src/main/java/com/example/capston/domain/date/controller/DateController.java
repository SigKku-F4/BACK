package com.example.capston.domain.date.controller;

import com.example.capston.aspect.Log;
import com.example.capston.domain.date.service.DateService;
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

@RestController
@RequiredArgsConstructor
public class DateController {

    private final DateService dateService;

    @GetMapping("/calendar/{date}")
    @Log
    public DateResponse.Calender getCalender(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd")LocalDate date,
                                             @LoginUser JwtDto jwtDto) {
        return dateService.getCalender(date, jwtDto.getEmail());
    }

    @PostMapping("/calendar/{date}")
    @Log
    public ResponseEntity<Response> addCalender(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
                                      @LoginUser JwtDto jwtDto,
                                      @RequestBody DateRequest.AddCalender request) {
        dateService.addCalender(date, jwtDto, request);
        return ResponseEntity.ok(new Response(true));
    }

    @PatchMapping("/calendar/{date}")
    @Log
    public ResponseEntity<Response> updateCalender(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
                                         @LoginUser JwtDto jwtDto,
                                         @RequestBody DateRequest.PatchCalender request){
        dateService.patchCalendar(date, jwtDto, request);
        return ResponseEntity.ok(new Response(true));
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response{
        boolean response;
    }
}
