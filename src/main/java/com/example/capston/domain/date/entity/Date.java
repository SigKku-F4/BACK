package com.example.capston.domain.date.entity;

import com.example.capston.domain.meal.entity.Meal;
import com.example.capston.domain.user.entity.Nutrient;
import com.example.capston.domain.user.entity.Stamp;
import com.example.capston.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "dates")
public class Date {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "date_id")
    private Long id;

    @Column(name = "user_email")
    private String user_email;

    @Column(name = "date_time")
    private LocalDate createdDate;

    @Column(name = "stamp")
    @Enumerated(value = EnumType.STRING)
    private Stamp stamp;

    @Column(name = "nutrient")
    private Nutrient nutrient;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(mappedBy = "date")
    private List<Meal> mealList = new ArrayList<>();

    public static Date createDate(LocalDate createdDate, User user){
        Date date = new Date();
        date.user=user;
        date.nutrient= new Nutrient();
        date.createdDate =createdDate;
        user.addDate(date);
        return date;
    }

    public void addMeal(Meal meal, Nutrient nutrient){
        nutrient.addNutrient(nutrient);
        this.mealList.add(meal);
    }

    public void setStamp(Stamp newStamp) {
        if(Objects.isNull(this.stamp) || this.stamp.equals(newStamp)){
            this.stamp = newStamp;
            Stamp.addStampCount(user, stamp);
        }else{
            if(this.stamp.equals(Stamp.RED)){
                user.minusRedStampCount();
            }
            else if(this.stamp.equals(Stamp.GREEN)){
                user.minusGreenStampCount();
            }
            else{
                user.minusYellowStampCount();
            }
            this.stamp=newStamp;
            Stamp.addStampCount(user, stamp);
        }
    }

    public void minusNutrient(Meal meal) {
        this.nutrient.minus(meal.getNutrient());
    }

    public void addNutrient(Meal meal){
        this.nutrient.addNutrient(meal.getNutrient());
    }

    public void deleteMeal(Meal meal) {
        this.mealList.remove(meal);
    }
}
