package com.example.capston.domain.foodList.repository;

import com.example.capston.domain.foodList.entity.FoodList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FoodListRepository extends JpaRepository<FoodList, Long> {

    Optional<FoodList> findByFoodName(String foodName);
}
