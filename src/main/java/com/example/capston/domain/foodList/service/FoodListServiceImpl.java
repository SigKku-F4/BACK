package com.example.capston.domain.foodList.service;

import com.example.capston.aspect.Log;
import com.example.capston.domain.config.CapPageRequest;
import com.example.capston.domain.date.entity.Date;
import com.example.capston.domain.date.repository.DateRepository;
import com.example.capston.domain.foodList.controller.FoodListResponse;
import com.example.capston.domain.foodList.entity.FoodList;
import com.example.capston.domain.foodList.entity.FoodOrderList;
import com.example.capston.domain.foodList.repository.FoodListQueryRepository;
import com.example.capston.domain.foodList.repository.FoodListRepository;
import com.example.capston.domain.user.entity.User;
import com.example.capston.domain.user.repository.UserRepository;
import com.example.capston.elastic.ElasticSearchOperations;
import com.example.capston.elastic.ElasticSearchRepository;
import com.example.capston.jwt.argumentresolver.JwtDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FoodListServiceImpl implements FoodListService {

    private final ElasticSearchRepository elasticSearchRepository;
    private final ElasticSearchOperations elasticSearchOperations;
    private final FoodListRepository foodListRepository;
    private final UserRepository userRepository;
    private final DateRepository dateRepository;

    @Override
    public FoodListResponse.GetList getFoodList(String foodName, CapPageRequest capPageRequest) {
        return null;
    }

    @Override
    public FoodListResponse.GetDetail getFoodDetail(Long foodName) {
        return null;
    }

    @Override
    public FoodListResponse.GetRecommendFoodListResponse getRecommendFoodList(LocalDate date, JwtDto jwtDto, CapPageRequest capPageRequest) {
        return null;
    }

    @Override
    public FoodListResponse.GetRecommendFoodListResponse getMyRecommendFoodList(LocalDate date, JwtDto jwtDto, CapPageRequest capPageRequest) {
        return null;
    }

    @Override
    public FoodListResponse.GetRecommendFoodListResponse getRecommendListAndFoodName(String foodName, LocalDate date, JwtDto jwtDto, CapPageRequest capPageRequest) {
        return null;
    }
}
