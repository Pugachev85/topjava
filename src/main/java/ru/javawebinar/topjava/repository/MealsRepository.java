package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealsRepository {
    void addMeal(Meal meal);

    List<Meal> getAllMeals();

    Meal updateMeal(Meal meal, Integer id);

    Integer getLastId();
}
