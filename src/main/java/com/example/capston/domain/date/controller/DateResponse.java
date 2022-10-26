package com.example.capston.domain.date.controller;

import com.example.capston.domain.date.entity.Date;
import com.example.capston.domain.food.entity.Food;
import com.example.capston.domain.image.entity.Image;
import com.example.capston.domain.meal.entity.Meal;
import com.example.capston.domain.meal.entity.MealType;
import com.example.capston.domain.user.entity.Nutrient;
import com.example.capston.domain.user.entity.Stamp;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DateResponse {

    @Data
    @NoArgsConstructor
    public static class Calender{
        Double totalKcal = 0.0;
        LocalDate date;
        Stamp stamp;
        Nutrient eatNutrient = new Nutrient();
        Nutrient needNutrient = new Nutrient();
        List<MealDto> meals = new ArrayList<>();

        public static Calender of(Date date, Nutrient needNutrient){
            Calender calender = new Calender();
            calender.needNutrient=needNutrient;
            calender.stamp = date.getStamp();
            calender.date = date.getCreatedDate();
            if(Objects.nonNull(date)){
                calender.totalKcal = date.getNutrient().getCalorie();
                calender.eatNutrient = date.getNutrient();
                calender.meals = date.getMealList().stream()
                        .map(MealDto::new)
                        .collect(Collectors.toList());
            }

            return calender;
        }
    }

    @Data
    @NoArgsConstructor
    public static class MealDto{
        Long mealId;
        MealType mealType;
        List<FoodDto> foods = new ArrayList<>();
        List<String> imgs = new ArrayList<>();

        public MealDto(Meal meal){
            this.mealId = meal.getId();
            this.mealType = meal.getMealType();
            this.imgs = meal.getImages().stream()
                    .map(Image::getImgs)
                    .collect(Collectors.toList());
            this.foods = meal.getFoodList()
                    .stream()
                    .map(FoodDto::new)
                    .collect(Collectors.toList());
        }
    }

    @Data
    @NoArgsConstructor
    public static class FoodDto{
        Long foodId;
        Long foodListId;
        String name;
        Double amount;
        Nutrient nutrient;

        public FoodDto(Food food){
            this.foodId = food.getId();
            this.name=food.getFoodName();
            this.nutrient = food.getNutrient();
            this.amount=food.getAmount();
            this.foodListId = food.getFoodList().getId();
        }
    }
}
