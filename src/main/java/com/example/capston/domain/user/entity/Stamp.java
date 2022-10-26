package com.example.capston.domain.user.entity;

import com.example.capston.domain.date.entity.Date;
import com.example.capston.domain.meal.entity.Meal;

import java.time.LocalDate;
import java.util.Objects;

public enum Stamp {

    GREEN, YELLOW, RED;

    public static void makeStamp(Date date, User user) {
        Stamp newStamp = compare(user, date);
        date.setStamp(newStamp);
    }

    public static Stamp compare(User user, Date date) {
        double cal = date.getNutrient().getCalorie() / user.getNeedNutrient().getCalorie() * 100;
        double protein = date.getNutrient().getProtein() / user.getNeedNutrient().getProtein() * 100;
        double car = date.getNutrient().getCarbohydrate() / user.getNeedNutrient().getCarbohydrate() * 100;

        if ((cal <= 25 || protein <= 25 || car <= 25) || (cal >= 176 || protein >= 176 || car >= 176)) {
            return RED;
        } else if (((cal >= 26 && cal <= 74) || (protein >= 26 && protein <= 74) || (car >= 26 && car <= 74))||
                ((cal >= 126 && cal <= 175) || (protein >= 126 && protein <= 175) || (car >= 126 && car <= 175))) {
            return YELLOW;
        } else {
            return GREEN;
        }
    }

    public static void addStampCount(User user, Stamp stamp) {
        if (stamp.equals(GREEN)) {
            user.addGreenStampCount();
        } else if (stamp.equals(YELLOW)) {
            user.addYellowStampCount();
        } else {
            user.addRedStampCount();
        }
    }
}
