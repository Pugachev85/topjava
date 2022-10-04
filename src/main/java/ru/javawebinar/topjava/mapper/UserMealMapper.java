package ru.javawebinar.topjava.mapper;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

public class UserMealMapper {
    public static UserMealWithExcess mapUserMealToUserMealWithExcess(UserMeal userMeal, boolean excess){
        return new UserMealWithExcess(
                userMeal.getDateTime(),
                userMeal.getDescription(),
                userMeal.getCalories(), 
                excess);
    }
}
