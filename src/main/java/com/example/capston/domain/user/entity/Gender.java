package com.example.capston.domain.user.entity;

import javax.persistence.Enumerated;


public enum Gender {
    MALE, FEMALE;

    public static Nutrient makeNutrient(Gender gender, Integer age, Integer height, Integer weight) {
        if (gender.equals(Gender.MALE)) {
            if (age >= 6 && age <= 9) {
                Double kcal = 88.5 - (61.9 * age) + 1.13 * (26.7 * weight + 903 + height) + 20;
                double percent = makePercent(kcal, 1700.0);
                return new Nutrient(percent * 700, percent * 1700, percent * 130, percent * 25, 0.0,
                        percent * 35, percent * 1700, percent * 1.3,
                        percent * 220, percent * 700, percent * 1200, percent * 2900,
                        percent * 150);
            } else if (age <= 11) {
                Double kcal = 88.5 - (61.9 * age) + 1.13 * (26.7 * weight + 903 + height) + 25;
                double percent = makePercent(kcal, 2000.0);
                return new Nutrient(percent * 700, percent * 2000, percent * 130, percent * 25, 0.0,
                        percent * 50, percent * 2000, percent * 1.7,
                        percent * 300, percent * 800, percent * 1500, percent * 3400,
                        percent * 220);
            } else if (age <= 14) {
                Double kcal = 88.5 - (61.9 * age) + 1.13 * (26.7 * weight + 903 + height) + 25;
                double percent = makePercent(kcal, 2500.0);
                return new Nutrient(percent * 700, percent * 2500, percent * 130, percent * 30, 0.0,
                        percent * 60, percent * 2400, percent * 2.3,
                        percent * 360, percent * 1000, percent * 1500, percent * 3500,
                        percent * 320);

            } else if (age <= 18) {
                Double kcal = 88.5 - (61.9 * age) + 1.13 * (26.7 * weight + 903 + height) + 25;
                double percent = makePercent(kcal, 2700.0);
                return new Nutrient(percent * 700, percent * 2700, percent * 130, percent * 30, 0.0,
                        percent * 65, percent * 2600, percent * 2.4,
                        percent * 400, percent * 900, percent * 1500, percent * 3500,
                        percent * 410);

            } else if (age <= 29) {
                Double kcal = 662 - (9.53 * age) + 1.11 * (15.91 * weight + 539.6 + height);
                double percent = makePercent(kcal, 2600.0);
                return new Nutrient(percent * 700, percent * 2600, percent * 130, percent * 30, 0.0,
                        percent * 65, percent * 2600, percent * 2.4,
                        percent * 400, percent * 800, percent * 1500, percent * 3500,
                        percent * 360);

            } else if (age <= 49) {
                Double kcal = 662 - (9.53 * age) + 1.11 * (15.91 * weight + 539.6 + height);
                double percent = makePercent(kcal, 2500.0);
                return new Nutrient(percent * 700, percent * 2500, percent * 130, percent * 30, 0.0,
                        percent * 65, percent * 2500, percent * 2.4,
                        percent * 400, percent * 800, percent * 1500, percent * 3500,
                        percent * 370);

            } else if (age <= 64) {
                Double kcal = 662 - (9.53 * age) + 1.11 * (15.91 * weight + 539.6 + height);
                double percent = makePercent(kcal, 2200.0);
                return new Nutrient(percent * 700, percent * 2200, percent * 130, percent * 30, 0.0,
                        percent * 60, percent * 2200, percent * 2.4,
                        percent * 400, percent * 750, percent * 1500, percent * 3500,
                        percent * 370);

            } else if (age <= 74) {
                Double kcal = 662 - (9.53 * age) + 1.11 * (15.91 * weight + 539.6 + height);
                double percent = makePercent(kcal, 2000.0);
                return new Nutrient(percent * 700, percent * 2000, percent * 130, percent * 25, 0.0,
                        percent * 60, percent * 2100, percent * 2.4,
                        percent * 400, percent * 700, percent * 1300, percent * 3500,
                        percent * 370);

            } else {
                Double kcal = 662 - (9.53 * age) + 1.11 * (15.91 * weight + 539.6 + height);
                double percent = makePercent(kcal, 1900.0);
                return new Nutrient(percent * 700, percent * 1900, percent * 130, percent * 25, 0.0,
                        percent * 60, percent * 2100, percent * 2.4,
                        percent * 400, percent * 700, percent * 1100, percent * 3500,
                        percent * 370);
            }

        } else {
            if (age >= 6 && age <= 18) {
                Double kcal = 35.3 - (30.8 * age) + 1.16 * (10 * weight + 934 + height) + 20;
                double percent = makePercent(kcal, 1500.0);
                return new Nutrient(percent * 700, percent * 1500, percent * 130, percent * 20, 0.0,
                        percent * 35, percent * 1600, percent * 2.4,
                        percent * 220, percent * 700, percent * 1200, percent * 2900,
                        percent * 150);

            } else if (age <= 11) {
                Double kcal = 35.3 - (30.8 * age) + 1.16 * (10 * weight + 934 + height) + 25;
                double percent = makePercent(kcal, 1800.0);
                return new Nutrient(percent * 700, percent * 1800, percent * 130, percent * 25, 0.0,
                        percent * 45, percent * 1900, percent * 1.3,
                        percent * 220, percent * 800, percent * 1100, percent * 2900,
                        percent * 220);

            } else if (age <= 14) {
                Double kcal = 35.3 - (30.8 * age) + 1.16 * (10 * weight + 934 + height) + 25;
                double percent = makePercent(kcal, 2000.0);
                return new Nutrient(percent * 700, percent * 2000, percent * 130, percent * 25, 0.0,
                        percent * 55, percent * 2000, percent * 1.7,
                        percent * 300, percent * 900, percent * 1100, percent * 3400,
                        percent * 290);

            } else if (age <= 18) {
                Double kcal = 35.3 - (30.8 * age) + 1.16 * (10 * weight + 934 + height) + 25;
                double percent = makePercent(kcal, 2000.0);
                return new Nutrient(percent * 700, percent * 2000, percent * 130, percent * 25, 0.0,
                        percent * 55, percent * 2000, percent * 2.3,
                        percent * 360, percent * 800, percent * 1100, percent * 3500,
                        percent * 340);

            } else if (age <= 29) {
                Double kcal = 354 - (6.91 * age) + 1.12 * (9.36 * weight + 726 + height);
                double percent = makePercent(kcal, 2000.0);
                return new Nutrient(percent * 700, percent * 2000, percent * 130, percent * 20, 0.0,
                        percent * 55, percent * 2100, percent * 2.4,
                        percent * 400, percent * 700, percent * 1100, percent * 3500,
                        percent * 280);

            } else if (age <= 49) {
                Double kcal = 354 - (6.91 * age) + 1.12 * (9.36 * weight + 726 + height);
                double percent = makePercent(kcal, 1900.0);
                return new Nutrient(percent * 700, percent * 1900, percent * 130, percent * 20, 0.0,
                        percent * 50, percent * 2000, percent * 2.4,
                        percent * 400, percent * 700, percent * 1100, percent * 3500,
                        percent * 280);
            } else if (age <= 64) {
                Double kcal = 354 - (6.91 * age) + 1.12 * (9.36 * weight + 726 + height);
                double percent = makePercent(kcal, 1700.0);
                return new Nutrient(percent * 700, percent * 1700, percent * 130, percent * 20, 0.0,
                        percent * 50, percent * 1900, percent * 2.4,
                        percent * 400, percent * 800, percent * 1500, percent * 3500,
                        percent * 280);
            } else if (age <= 74) {
                Double kcal = 354 - (6.91 * age) + 1.12 * (9.36 * weight + 726 + height);
                double percent = makePercent(kcal, 1600.0);
                return new Nutrient(percent * 700, percent * 1600, percent * 130, percent * 20, 0.0,
                        percent * 50, percent * 1800, percent * 2.4,
                        percent * 400, percent * 800, percent * 1300, percent * 3500,
                        percent * 280);
            } else {
                Double kcal = 354 - (6.91 * age) + 1.12 * (9.36 * weight + 726 + height);
                double percent = makePercent(kcal, 1500.0);
                return new Nutrient(percent * 700, percent * 1500, percent * 130, percent * 20, 0.0,
                        percent * 50, percent * 1800, percent * 2.4,
                        percent * 400, percent * 800, percent * 1100, percent * 3500,
                        percent * 280);
            }
        }
    }

    public static double makePercent(Double targetKcal, Double normalKcal) {
        return normalKcal / targetKcal;
    }
}
