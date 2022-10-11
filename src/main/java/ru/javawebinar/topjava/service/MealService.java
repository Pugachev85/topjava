package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealService {

        void newMeal(Meal meal);

        List<Meal> getAllMeals();

        Meal updateMeal(Meal meal, Integer id);

        Integer getLastId();
}
