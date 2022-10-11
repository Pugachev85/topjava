package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.ImplMealsRepositoryInRam;
import ru.javawebinar.topjava.repository.MealsRepository;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

@Service
public class ImplMealService implements MealService {

    MealsRepository mealsRepo = ImplMealsRepositoryInRam.getInstance();

    @Override
    public void newMeal(Meal meal) {
        mealsRepo.addMeal(meal);
    }

    @Override
    public List<Meal> getAllMeals() {
        if (getLastId().equals(-1)) initialize();
        return mealsRepo.getAllMeals();
    }

    @Override
    public Meal updateMeal(Meal meal, Integer id) {
        return mealsRepo.updateMeal(meal, id);
    }

    @Override
    public Integer getLastId() {
        return mealsRepo.getLastId();
    }

    public void initialize() {
        newMeal(new Meal(getLastId() + 1, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        newMeal(new Meal(getLastId() + 1, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        newMeal(new Meal(getLastId() + 1, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        newMeal(new Meal(getLastId() + 1, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        newMeal(new Meal(getLastId() + 1, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        newMeal(new Meal(getLastId() + 1, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        newMeal(new Meal(getLastId() + 1, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }
}
