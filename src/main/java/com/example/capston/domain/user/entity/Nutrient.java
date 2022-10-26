package com.example.capston.domain.user.entity;

import com.example.capston.domain.meal.entity.Meal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Nutrient {

    // 인
    @Column(name = "phosphorus")
    private Double phosphorus = 0.0;

    @Column(name = "calorie")
    private Double calorie = 0.0;

    @Column(name = "carbohydrate")
    private Double carbohydrate = 0.0;

    @Column(name = "dietary_fiber")
    private Double dietaryFiber = 0.0;

    @Column(name = "fat")
    private Double fat = 0.0;

    @Column(name = "protein")
    private Double protein = 0.0;

    @Column(name = "moisture")
    private Double moisture = 0.0;

    @Column(name = "vitamin_b12")
    private Double vitaminB12 = 0.0;

    // 엽산
    @Column(name = "folic_acid")
    private Double folicAcid = 0.0;

    @Column(name = "calcium")
    private Double calcium = 0.0;

    // 나트륨
    @Column(name = "sodium")
    private Double sodium = 0.0;

    //칼륨
    @Column(name = "potassium")
    private Double potassium = 0.0;

    @Column(name = "magnesium")
    private Double magnesium = 0.0;

    public void addNutrient(Nutrient nutrient) {
        this.phosphorus += nutrient.getPhosphorus();
        this.calorie += nutrient.getCalorie();
        this.carbohydrate += nutrient.getCarbohydrate();
        this.dietaryFiber += nutrient.getDietaryFiber();
        this.fat += nutrient.getFat();
        this.protein += nutrient.getProtein();
        this.moisture += nutrient.getMoisture();
        this.vitaminB12 += nutrient.getVitaminB12();
        this.folicAcid += nutrient.getFolicAcid();
        this.calcium += nutrient.getCalcium();
        this.sodium += nutrient.getSodium();
        this.potassium += nutrient.getPotassium();
        this.magnesium += nutrient.getMagnesium();
    }

    public static Nutrient makePercent(Nutrient nutrient, Double percent) {
        Nutrient current = new Nutrient();
        current.phosphorus += (nutrient.getPhosphorus() * percent);
        current.calorie += (nutrient.getCalorie() * percent);
        current.carbohydrate += (nutrient.getCarbohydrate() * percent);
        current.dietaryFiber += (nutrient.getDietaryFiber() * percent);
        current.fat += (nutrient.getFat() * percent);
        current.protein += (nutrient.getProtein() * percent);
        current.moisture += (nutrient.getMoisture() * percent);
        current.vitaminB12 += (nutrient.getVitaminB12() * percent);
        current.folicAcid += (nutrient.getFolicAcid() * percent);
        current.calcium += (nutrient.getCalcium() * percent);
        current.sodium += (nutrient.getSodium() * percent);
        current.potassium += (nutrient.getPotassium() * percent);
        current.magnesium += (nutrient.getMagnesium() * percent);

        return current;
    }

    public void minus(Nutrient nutrient) {
        this.phosphorus -= nutrient.getPhosphorus();
        this.calorie -= nutrient.getCalorie();
        this.carbohydrate -= nutrient.getCarbohydrate();
        this.dietaryFiber -= nutrient.getDietaryFiber();
        this.fat -= nutrient.getFat();
        this.protein -= nutrient.getProtein();
        this.moisture -= nutrient.getMoisture();
        this.vitaminB12 -= nutrient.getVitaminB12();
        this.folicAcid -= nutrient.getFolicAcid();
        this.calcium -= nutrient.getCalcium();
        this.sodium -= nutrient.getSodium();
        this.potassium -= nutrient.getPotassium();
        this.magnesium -= nutrient.getMagnesium();
    }
}
