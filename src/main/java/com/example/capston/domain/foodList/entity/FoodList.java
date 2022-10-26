package com.example.capston.domain.foodList.entity;

import com.example.capston.domain.food.entity.Food;
import com.example.capston.domain.meal.entity.Meal;
import com.example.capston.domain.user.entity.Nutrient;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@AllArgsConstructor
public class FoodList {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "food_id")
    private Long id;

    @Column(name = "food_name")
    private String foodName;

    @Column(name = "one_time_supply")
    private Double oneTimeSupply;

    @Column(name = "nutrient")
    private Nutrient nutrient;

    @OneToMany(mappedBy = "foodList")
    private List<Food> foods = new ArrayList<>();

    @Column(name = "category")
    private String category;

    public Food toFood(Meal meal, Double amount, String userEmail){
        Nutrient nutrient = Nutrient.makePercent(this.nutrient, amount/oneTimeSupply);
        return Food.createFood(this.foodName, meal, amount, nutrient, userEmail, this);
    }
}
