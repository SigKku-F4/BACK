package com.example.capston.domain.meal.repository;

import com.example.capston.domain.date.entity.Date;
import com.example.capston.domain.meal.entity.Meal;
import com.example.capston.domain.meal.entity.MealType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MealRepository extends JpaRepository<Meal, Long> {

    Optional<Meal> findByDate(Date date);

    Optional<Meal> findByDateAndMealType(Date date, MealType mealType);
}
