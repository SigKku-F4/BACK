package com.example.capston.domain.foodList.service;

import com.example.capston.domain.config.CapPageRequest;
import com.example.capston.domain.foodList.controller.FoodListResponse;
import com.example.capston.jwt.argumentresolver.JwtDto;

import java.time.LocalDate;
import java.util.List;

public interface FoodListService {

    FoodListResponse.GetList getFoodList(String foodName, CapPageRequest capPageRequest);

    FoodListResponse.GetDetail getFoodDetail(Long foodName);

    FoodListResponse.GetRecommendFoodListResponse getRecommendFoodList(LocalDate date, JwtDto jwtDto, CapPageRequest capPageRequest);

    FoodListResponse.GetRecommendFoodListResponse getMyRecommendFoodList(LocalDate date, JwtDto jwtDto, CapPageRequest capPageRequest);

    FoodListResponse.GetRecommendFoodListResponse getRecommendListAndFoodName(String foodName, LocalDate date, JwtDto jwtDto, CapPageRequest capPageRequest);
}
