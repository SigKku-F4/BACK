package com.example.capston.domain.date.controller;

import com.example.capston.domain.meal.entity.MealType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class DateRequest {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddCalender {
        MealType mealType;
        List<Imgs> images;
        List<AddFood> foods;
        String description;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddFood{
        Long foodListId;
        double amount;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeleteCalender {
        MealType mealType;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PatchCalender {
        Long mealId;
        MealType mealType;
        List<Imgs> images;
        List<PatchFood> foods;
        String description;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PatchFood {
        Long foodId;
        Long foodListId;
        double amount;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Imgs {
        String image;
    }
}
