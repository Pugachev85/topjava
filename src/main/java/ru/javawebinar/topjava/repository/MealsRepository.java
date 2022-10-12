package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealsRepository {
    Meal add(Meal meal);

    List<Meal> getAll();

    Meal update(Meal meal);

    void delete(Integer id);

    Meal findById(Integer id);
}
