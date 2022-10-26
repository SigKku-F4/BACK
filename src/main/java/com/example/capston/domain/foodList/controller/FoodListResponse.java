package com.example.capston.domain.foodList.controller;

import com.example.capston.domain.foodList.entity.FoodList;
import com.example.capston.domain.user.entity.Nutrient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class FoodListResponse {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetList {
        List<FoodList> foodList;
        Long totalElements;
        Integer pageNumber;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FoodList{
        Long foodListId;
        String foodName;
        String category;
        Double kcal;
        Double oneSupplyAmount;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetDetail{
        Long foodListId;
        String category;
        String foodName;
        Double oneSupplyAmount;
        Nutrient nutrient;
    }

    @Data
    @NoArgsConstructor
    public static class GetRecommendFoodListResponse {

        List<GetRecommendFoodList> foodLists;
        PageMeta pageMeta;

        String recommendComments;

        public GetRecommendFoodListResponse(List<com.example.capston.domain.foodList.entity.FoodList> foodLists, Page<com.example.capston.domain.foodList.entity.FoodList> page,
                                            String recommendComments){
            this.foodLists = foodLists.stream()
                    .map(a -> new FoodListResponse.GetRecommendFoodList(a.getId(),a.getFoodName(),a.getCategory(),a.getOneTimeSupply(), a.getNutrient()))
                    .collect(Collectors.toList());
            this.pageMeta = new PageMeta(page.getTotalElements(), page.getNumber());
            this.recommendComments = recommendComments;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetRecommendFoodList{
        Long foodListId;
        String foodName;
        String category;
        Double oneSupplyAmount;
        Nutrient nutrient;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PageMeta{
        Long totalElements;
        Integer nowPage;
    }
}
