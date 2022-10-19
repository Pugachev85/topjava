package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> this.save(meal, 1));
        MealsUtil.meals2.forEach(meal -> this.save(meal, 2));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            repository.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        int id = meal.getId();
        if (repository.containsKey(id) && repository.get(id).getUserId().equals(userId)){
            return repository.computeIfPresent(meal.getId(), (key, oldMeal) -> meal);
        }
        return null;
    }

    @Override
    public boolean delete(int id, int userId) {
        if (!repository.containsKey(id)) return false;
        return repository.get(id).getUserId().equals(userId) && repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        if (!repository.containsKey(id)) return null;
        Meal meal = repository.get(id);
        return meal.getUserId().equals(userId) ? meal : null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return repository.values().stream()
                .filter(meal -> meal.getUserId().equals(userId))
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Meal> getBetweenDates(LocalDate startDate, LocalDate endDate, int userId) {
        return repository.values().stream()
                .filter(meal -> meal.getUserId().equals(userId)
                        && meal.getDate().isAfter(startDate.minusDays(1))
                        && meal.getDate().isBefore(endDate.plusDays(1)))
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

