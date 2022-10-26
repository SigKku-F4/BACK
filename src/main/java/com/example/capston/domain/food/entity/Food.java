package com.example.capston.domain.food.entity;

import com.example.capston.domain.foodList.entity.FoodList;
import com.example.capston.domain.meal.entity.Meal;
import com.example.capston.domain.user.entity.Nutrient;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "food_id")
    private Long id;

    @Column(name = "food_name")
    private String foodName;

    @Column(name = "nutrient")
    private Nutrient nutrient;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "count")
    private Integer eatCount;

    @Column(name = "user_email")
    private String userEmail;

    @JoinColumn(name = "meal_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Meal meal;

    @JoinColumn(name = "food_list_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private FoodList foodList;

    public static Food createFood(String foodName, Meal meal, Double amount,
                                  Nutrient nutrient, String userEmail, FoodList foodList) {
        Food food = new Food();
        food.foodName = foodName;
        food.meal = meal;
        food.amount = amount;
        food.nutrient = nutrient;
        food.eatCount = 1;
        food.userEmail = userEmail;
        food.foodList = foodList;
        meal.addNutrient(nutrient);
        return food;
    }

    public void addEatCount(){
        this.eatCount+=1;
    }

    public void makeNutrient(double amount) {
        Double oneTimeSupply = this.foodList.getOneTimeSupply();
        this.nutrient = Nutrient.makePercent(foodList.getNutrient(), amount/oneTimeSupply);
        meal.addNutrient(nutrient);
    }

    public void minusCount() {
        this.eatCount-=1;
    }

    public void zeroNutrient() {
        this.nutrient= new Nutrient();
    }

    public void changeAmount(double amount) {
        this.amount = amount;
    }
}
