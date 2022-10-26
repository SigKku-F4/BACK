package com.example.capston;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//@RestControllerAdvice(basePackages = "com.example.capston")
public class CapControllerAdvice {


    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response ex(IllegalArgumentException e){
        return new Response(false);
    }



    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response{
        boolean response;
    }
}
