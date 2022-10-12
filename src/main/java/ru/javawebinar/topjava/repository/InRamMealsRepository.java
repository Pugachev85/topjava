package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InRamMealsRepository implements MealsRepository {

    private volatile Map<Integer, Meal> storage = new ConcurrentHashMap<>();
    private Integer lastId = 0;

    @Override
    public Meal add(Meal meal) {
        meal.setId(lastId += 1);
        return storage.put(lastId, meal);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Meal update(Meal meal) {
        return storage.replace(meal.getId(), meal);
    }

    @Override
    public void delete(Integer id) {
        storage.remove(id);
    }

    @Override
    public Meal findById(Integer id) {
        return storage.get(id);
    }
}
