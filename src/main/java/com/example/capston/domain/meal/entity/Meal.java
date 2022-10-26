package com.example.capston.domain.meal.entity;

import com.example.capston.domain.date.entity.Date;
import com.example.capston.domain.food.entity.Food;
import com.example.capston.domain.image.entity.Image;
import com.example.capston.domain.user.entity.Nutrient;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Meal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "meal_id")
    private Long id;

    @Column(name = "nutrient")
    private Nutrient nutrient;

    @JoinColumn(name = "date_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Date date;

    @Enumerated(value = EnumType.STRING)
    private MealType mealType;

    @Column(name = "description")
    private String description;


    @OneToMany(mappedBy = "meal")
    private List<Image> images = new ArrayList<>();

    @OneToMany(mappedBy = "meal", cascade = CascadeType.ALL)
    private List<Food> foodList = new ArrayList<>();

    public static Meal createMeal(Date date, MealType mealType, String description) {
        Meal meal = new Meal();
        meal.date = date;
        meal.mealType = mealType;
        meal.description = description;
        meal.nutrient = new Nutrient();
        date.addMeal(meal, new Nutrient());
        return meal;
    }

    public void addFood(List<Food> foodList) {
        this.foodList = foodList;
        this.nutrient = new Nutrient();
        for(Food food: foodList){
            this.nutrient.addNutrient(food.getNutrient());
        }
        this.foodList = foodList;
        this.date.getNutrient().addNutrient(this.nutrient);
    }

    public void addOneFood(Food food){
        this.foodList.add(food);
    }

    public void addNutrient(Nutrient nutrient) {
        this.nutrient.addNutrient(nutrient);
    }

    public void addImages(Image image){
        this.images.add(image);
    }

    public void zeroNutrient() {
        this.nutrient = new Nutrient();
    }

    public void deleteFoodList(Food food) {
        this.foodList.remove(food);
    }

    public void removeImage() {
        this.images = new ArrayList<>();
    }
}
