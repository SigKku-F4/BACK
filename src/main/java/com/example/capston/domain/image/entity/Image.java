package com.example.capston.domain.image.entity;

import com.example.capston.domain.meal.entity.Meal;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "imgs")
    private String imgs;

    @JoinColumn(name = "meal_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Meal meal;


    public static Image createImage(Meal meal, String imgs){
        Image image = new Image();
        image.meal=meal;
        image.imgs=imgs;
        meal.addImages(image);
        return image;
    }

}
