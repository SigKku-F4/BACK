package com.example.capston.domain.food.repository;

import com.example.capston.domain.food.entity.Food;
import com.example.capston.domain.meal.entity.Meal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FoodRepository extends JpaRepository<Food, Long> {

    Optional<Food> findByUserEmailAndIdAndMeal(String userEmail, Long foodId, Meal meal);

}