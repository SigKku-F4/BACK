package com.example.capston.domain.foodList.repository;

import com.example.capston.domain.food.entity.QFood;
import com.example.capston.domain.foodList.entity.FoodList;
import com.example.capston.domain.foodList.entity.FoodOrderList;
import com.example.capston.domain.foodList.service.FoodListServiceImpl;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.capston.domain.food.entity.QFood.*;
import static com.example.capston.domain.foodList.entity.QFoodList.foodList;
import static com.querydsl.core.types.Order.*;

@Repository
@RequiredArgsConstructor
public class FoodListQueryRepository {

    private final JPAQueryFactory query;

    public Page<FoodList> getList(String foodName, Pageable pageable) {
        List<FoodList> content = query.selectFrom(foodList)
                .where(containsFoodName(foodName))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        Long totalCount = query.select(Wildcard.count)
                .from(foodList)
                .where(containsFoodName(foodName))
                .fetch().get(0);


        return PageableExecutionUtils.getPage(content, pageable, () -> totalCount);
    }

    public BooleanExpression containsFoodName(String foodName) {
        return foodName != null ? foodList.foodName.contains(foodName) : null;
    }

    public Page<FoodList> getRecommendList(List<FoodListServiceImpl.OrderList> orderLists, String userEmail, Pageable pageable) {

        List<OrderSpecifier> orderSpecifiers = makeOrderSpecifier(orderLists);

        List<FoodList> content = query.selectFrom(foodList)
                .orderBy(orderSpecifiers.toArray(OrderSpecifier[]::new))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        Long totalCount = query.select(Wildcard.count)
                .from(foodList)
                .fetch().get(0);

        return PageableExecutionUtils.getPage(content, pageable, () -> totalCount);
    }

    public Page<FoodList> getMyRecommendList(List<FoodListServiceImpl.OrderList> orderLists, String userEmail, Pageable pageable) {


        List<OrderSpecifier> orderSpecifier = makeOrderSpecifier(orderLists);

        List<FoodList> content = query.select(foodList).distinct()
                .from(foodList)
                .leftJoin(foodList.foods, food)
                .where(food.userEmail.eq(userEmail))
                .orderBy(orderSpecifier.toArray(OrderSpecifier[]::new))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        Long totalCount = query.select(foodList.id.countDistinct())
                .from(foodList)
                .leftJoin(foodList.foods, food)
                .where(foodList.foodName.eq(food.foodName), food.userEmail.eq(userEmail))
                .fetch().get(0);

        return PageableExecutionUtils.getPage(content, pageable, () -> totalCount);
    }

    public Page<FoodList> getRecommendAndFoodNameList(List<FoodListServiceImpl.OrderList> orderLists, String foodName, Pageable pageable) {

        List<OrderSpecifier> orderSpecifier = makeOrderSpecifier(orderLists);

        orderSpecifier.add(0, food.eatCount.desc().nullsLast());

        List<FoodList> content = query.select(foodList).distinct()
                .from(foodList)
                .leftJoin(foodList.foods, food)
                .where(containsFoodName(foodName))
                .orderBy(orderSpecifier.toArray(OrderSpecifier[]::new))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        Long totalCount = query.select(Wildcard.count)
                .from(foodList)
                .where(containsFoodName(foodName))
                .fetch().get(0);

        return PageableExecutionUtils.getPage(content, pageable, () -> totalCount);
    }

    public List<OrderSpecifier> makeOrderSpecifier(List<FoodListServiceImpl.OrderList> orderLists) {

        List<OrderSpecifier> orderSpecifiers = new ArrayList<>();

        orderSpecifiers.add(createOrder(orderLists));

        return orderSpecifiers;
    }

    private OrderSpecifier createOrder(List<FoodListServiceImpl.OrderList> a) {

        FoodListServiceImpl.OrderList first = a.get(0);
        FoodListServiceImpl.OrderList second = a.get(1);
        if (first.getFoodOrderList() == FoodOrderList.CARBOHYDRATE) {
            if(second.getFoodOrderList() == FoodOrderList.PROTEIN){
                return new OrderSpecifier(DESC, foodList.nutrient.carbohydrate.multiply(
                        first.getPercent()).add(foodList.nutrient.protein.multiply(second.getPercent())));
            }
            else if(second.getFoodOrderList() == FoodOrderList.VITAMIN){
                return new OrderSpecifier(DESC, foodList.nutrient.carbohydrate.multiply(
                        first.getPercent()).add(foodList.nutrient.vitaminB12.multiply(second.getPercent())));
            }
            else if(second.getFoodOrderList() == FoodOrderList.CALCIUM){
                return new OrderSpecifier(DESC, foodList.nutrient.carbohydrate.multiply(
                        first.getPercent()).add(foodList.nutrient.calcium.multiply(second.getPercent())));
            }
            else{
                return new OrderSpecifier(DESC, foodList.nutrient.carbohydrate.multiply(
                        first.getPercent()).add(foodList.nutrient.potassium.multiply(second.getPercent())));
            }
        } else if (first.getFoodOrderList() == FoodOrderList.PROTEIN) {
            if(second.getFoodOrderList() == FoodOrderList.CARBOHYDRATE){
                return new OrderSpecifier(DESC, foodList.nutrient.protein.multiply(
                        first.getPercent()).add(foodList.nutrient.carbohydrate.multiply(second.getPercent())));
            }
            else if(second.getFoodOrderList() == FoodOrderList.VITAMIN){
                return new OrderSpecifier(DESC, foodList.nutrient.protein.multiply(
                        first.getPercent()).add(foodList.nutrient.vitaminB12.multiply(second.getPercent())));
            }
            else if(second.getFoodOrderList() == FoodOrderList.CALCIUM){
                return new OrderSpecifier(DESC, foodList.nutrient.protein.multiply(
                        first.getPercent()).add(foodList.nutrient.calcium.multiply(second.getPercent())));
            }
            else{
                return new OrderSpecifier(DESC, foodList.nutrient.protein.multiply(
                        first.getPercent()).add(foodList.nutrient.potassium.multiply(second.getPercent())));
            }
        } else if (first.getFoodOrderList() == FoodOrderList.VITAMIN) {
            if(second.getFoodOrderList() == FoodOrderList.CARBOHYDRATE){
                return new OrderSpecifier(DESC, foodList.nutrient.vitaminB12.multiply(
                        first.getPercent()).add(foodList.nutrient.carbohydrate.multiply(second.getPercent())));
            }
            else if(second.getFoodOrderList() == FoodOrderList.PROTEIN){
                return new OrderSpecifier(DESC, foodList.nutrient.vitaminB12.multiply(
                        first.getPercent()).add(foodList.nutrient.protein.multiply(second.getPercent())));
            }
            else if(second.getFoodOrderList() == FoodOrderList.CALCIUM){
                return new OrderSpecifier(DESC, foodList.nutrient.vitaminB12.multiply(
                        first.getPercent()).add(foodList.nutrient.calcium.multiply(second.getPercent())));
            }
            else{
                return new OrderSpecifier(DESC, foodList.nutrient.vitaminB12.multiply(
                        first.getPercent()).add(foodList.nutrient.potassium.multiply(second.getPercent())));
            }
        } else if (first.getFoodOrderList() == FoodOrderList.CALCIUM) {
            if(second.getFoodOrderList() == FoodOrderList.CARBOHYDRATE){
                return new OrderSpecifier(DESC, foodList.nutrient.calcium.multiply(
                        first.getPercent()).add(foodList.nutrient.carbohydrate.multiply(second.getPercent())));
            }
            else if(second.getFoodOrderList() == FoodOrderList.PROTEIN){
                return new OrderSpecifier(DESC, foodList.nutrient.calcium.multiply(
                        first.getPercent()).add(foodList.nutrient.protein.multiply(second.getPercent())));
            }
            else if(second.getFoodOrderList() == FoodOrderList.VITAMIN){
                return new OrderSpecifier(DESC, foodList.nutrient.calcium.multiply(
                        first.getPercent()).add(foodList.nutrient.vitaminB12.multiply(second.getPercent())));
            }
            else{
                return new OrderSpecifier(DESC, foodList.nutrient.calcium.multiply(
                        first.getPercent()).add(foodList.nutrient.potassium.multiply(second.getPercent())));
            }
        } else {
            if(second.getFoodOrderList() == FoodOrderList.CARBOHYDRATE){
                return new OrderSpecifier(DESC, foodList.nutrient.potassium.multiply(
                        first.getPercent()).add(foodList.nutrient.carbohydrate.multiply(second.getPercent())));
            }
            else if(second.getFoodOrderList() == FoodOrderList.PROTEIN){
                return new OrderSpecifier(DESC, foodList.nutrient.potassium.multiply(
                        first.getPercent()).add(foodList.nutrient.protein.multiply(second.getPercent())));
            }
            else if(second.getFoodOrderList() == FoodOrderList.VITAMIN){
                return new OrderSpecifier(DESC, foodList.nutrient.potassium.multiply(
                        first.getPercent()).add(foodList.nutrient.vitaminB12.multiply(second.getPercent())));
            }
            else{
                return new OrderSpecifier(DESC, foodList.nutrient.potassium.multiply(
                        first.getPercent()).add(foodList.nutrient.calcium.multiply(second.getPercent())));
            }
        }
    }
}
