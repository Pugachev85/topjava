package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImplMealsRepositoryInRam implements MealsRepository {

    private volatile static ImplMealsRepositoryInRam instance;

    private ImplMealsRepositoryInRam() {
    }

    public static ImplMealsRepositoryInRam getInstance() {
        if (instance == null) {
            synchronized (ImplMealsRepositoryInRam.class) {
                if (instance == null) {
                    instance = new ImplMealsRepositoryInRam();
                }
            }
        }
        return instance;
    }

    private volatile Map<Integer, Meal> mealsStorage = new HashMap<>();

    @Override
    public void addMeal(Meal meal) {
        mealsStorage.put(meal.getId(), meal);
    }

    @Override
    public List<Meal> getAllMeals() {
        return new ArrayList<>(mealsStorage.values());
    }

    @Override
    public Meal updateMeal(Meal meal, Integer id) {
        return mealsStorage.replace(id, meal);
    }

    @Override
    public Integer getLastId() {
        if (mealsStorage.isEmpty()) return -1;
        return mealsStorage.size();
    }
}
