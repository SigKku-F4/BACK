package com.example.capston.domain.foodList.entity;

import lombok.Getter;

@Getter
public enum FoodOrderList {
    CARBOHYDRATE("탄수화물 섭취가 필요해요"),
    PROTEIN("단백질 섭취가 필요해요"),
    VITAMIN("비타민 섭취가 필요해요"),
    CALCIUM("칼슘 섭취가 필요해요"),
    POTASSIUM("칼륨 섭취가 필요해요");

    private final String description;

    FoodOrderList(String description) {
        this.description = description;
    }
}
