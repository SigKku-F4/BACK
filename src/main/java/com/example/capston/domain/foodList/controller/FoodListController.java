package com.example.capston.domain.foodList.controller;

import com.example.capston.aspect.Log;
import com.example.capston.domain.config.CapPageRequest;
import com.example.capston.domain.foodList.service.FoodListService;
import com.example.capston.jwt.argumentresolver.JwtDto;
import com.example.capston.jwt.argumentresolver.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class FoodListController {

    private final FoodListService foodListService;

    @GetMapping("/foods/{foodName}")
    @Log
    FoodListResponse.GetList getList(@PathVariable(required = false) String foodName, CapPageRequest capPageRequest){
        FoodListResponse.GetList foodList = foodListService.getFoodList(foodName, capPageRequest);
        return foodList;
    }

    @GetMapping("/foods")
    @Log
    public FoodListResponse.GetDetail getDetail(@RequestParam(name = "foodListId") Long foodListId){
        return foodListService.getFoodDetail(foodListId);
    }

    @GetMapping("/foods/recommend/{date}")
    @Log
    public FoodListResponse.GetRecommendFoodListResponse getRecommendList(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
                                                                          @LoginUser JwtDto jwtDto,
                                                                          CapPageRequest capPageRequest){
        return foodListService.getRecommendFoodList(date, jwtDto, capPageRequest);
    }

    @GetMapping("/foods/recommend/my/{date}")
    @Log
    public FoodListResponse.GetRecommendFoodListResponse getMyRecommendList(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
                                                                          @LoginUser JwtDto jwtDto,
                                                                          CapPageRequest capPageRequest){
        return foodListService.getMyRecommendFoodList(date, jwtDto, capPageRequest);
    }

    @GetMapping("/foods/recommend")
    @Log
    public FoodListResponse.GetRecommendFoodListResponse getRecommendListAndFoodName(@RequestParam(name = "foodName") String foodName,
                                                                                     @DateTimeFormat(pattern = "yyyy-MM-dd") @RequestParam(name = "date") LocalDate date,
                                                                                     @LoginUser JwtDto jwtDto, CapPageRequest capPageRequest){
        return foodListService.getRecommendListAndFoodName(foodName, date, jwtDto, capPageRequest);
    }
}
