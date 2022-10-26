package com.example.capston.domain.date.service;

import com.example.capston.aspect.Log;
import com.example.capston.domain.date.controller.DateRequest;
import com.example.capston.domain.date.controller.DateResponse;
import com.example.capston.domain.date.entity.Date;
import com.example.capston.domain.date.repository.DateRepository;
import com.example.capston.domain.food.entity.Food;
import com.example.capston.domain.food.repository.FoodRepository;
import com.example.capston.domain.foodList.repository.FoodListRepository;
import com.example.capston.domain.image.entity.Image;
import com.example.capston.domain.image.repository.ImageRepository;
import com.example.capston.domain.meal.entity.Meal;
import com.example.capston.domain.meal.repository.MealRepository;
import com.example.capston.domain.user.entity.Stamp;
import com.example.capston.domain.user.entity.User;
import com.example.capston.domain.user.repository.UserRepository;
import com.example.capston.jwt.argumentresolver.JwtDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DateServiceImpl implements DateService {

    private final DateRepository dateRepository;
    private final UserRepository userRepository;
    private final FoodListRepository foodListRepository;
    private final MealRepository mealRepository;
    private final FoodRepository foodRepository;

    private final ImageRepository imageRepository;

    @Override
    @Log
    public DateResponse.Calender getCalender(LocalDate date, String email) {
        User user = userRepository.findByEmail(email).get();

        Date current = dateRepository.findByUserEmailAndCreatedDate(email, date)
                .orElse(Date.createDate(date, user));

        return DateResponse.Calender.of(current, user.getNeedNutrient());
    }

    @Override
    @Log
    public void addCalender(LocalDate date, JwtDto jwtDto, DateRequest.AddCalender request) {
        User user = userRepository.findByEmail(jwtDto.getEmail()).get();

        Date current = dateRepository.findByCreatedDate(date)
                .orElse(Date.createDate(date, user));

        dateRepository.save(current);

        Meal meal = Meal.createMeal(current, request.getMealType(), request.getDescription());

        List<Food> foodList = new ArrayList<>();

        for (DateRequest.AddFood food : request.getFoods()) {

            Food nowFood = foodListRepository.findById(food.getFoodListId()).get().toFood(meal, food.getAmount(), jwtDto.getEmail());

            foodList.add(nowFood);

        }

        meal.addFood(foodList);

        mealRepository.save(meal);

        for(DateRequest.Imgs img: request.getImages()){
            Image image = Image.createImage(meal, img.getImage());
            imageRepository.save(image);
        }

        Stamp.makeStamp(current, user);
    }

    @Override
    public void patchCalendar(LocalDate date, JwtDto jwtDto, DateRequest.PatchCalender request) {
        User user = userRepository.findByEmail(jwtDto.getEmail()).get();

        Date current = dateRepository.findByCreatedDate(date)
                .orElse(Date.createDate(date, user));

        Meal meal = mealRepository.findById(request.getMealId()).get();

        current.minusNutrient(meal);

        meal.removeImage();

        meal.zeroNutrient();

        List<Long> foodsId = request.getFoods().stream()
                .map(DateRequest.PatchFood::getFoodId)
                .collect(Collectors.toList());

        List<Long> collect = meal.getFoodList()
                .stream()
                .filter(a -> {
                    if (!foodsId.contains(a.getId())) {
                        return true;
                    }else{
                        return false;
                    }
                })
                .map(Food::getId)
                .collect(Collectors.toList());

        collect.forEach(a -> {
            Food food = foodRepository.findById(a).get();
            foodRepository.delete(food);
            meal.deleteFoodList(food);
        });

        for (DateRequest.PatchFood food : request.getFoods()) {

            Optional<Food> optionalNowFood = foodRepository.findByUserEmailAndIdAndMeal(jwtDto.getEmail(), food.getFoodId(), meal);

            Food nowFood;

            boolean isNewFood = false;

            if (optionalNowFood.isEmpty()) {
                nowFood = foodListRepository.findById(food.getFoodListId()).get().toFood(meal, food.getAmount(), jwtDto.getEmail());
                isNewFood = true;
            } else {
                nowFood = optionalNowFood.get();
                nowFood.changeAmount(food.getAmount());
                nowFood.makeNutrient(food.getAmount());
            }
            if(isNewFood){
                meal.addOneFood(nowFood);
            }
        }

        List<Image> images = imageRepository.findByMeal(meal);

        for(DateRequest.Imgs img: request.getImages()){
            Image image = Image.createImage(meal, img.getImage());
            imageRepository.save(image);
        }

        current.addNutrient(meal);

        Stamp.makeStamp(current, user);

        imageRepository.deleteAll(images);

        if(meal.getFoodList().isEmpty()){
            current.deleteMeal(meal);
            mealRepository.delete(meal);
        }
    }

}
