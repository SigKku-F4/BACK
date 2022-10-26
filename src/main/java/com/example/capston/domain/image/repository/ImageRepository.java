package com.example.capston.domain.image.repository;

import com.example.capston.domain.image.entity.Image;
import com.example.capston.domain.meal.entity.Meal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {


    List<Image> findByMeal(Meal meal);
}
