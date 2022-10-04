package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static ru.javawebinar.topjava.mapper.UserMealMapper.mapUserMealToUserMealWithExcess;
import static ru.javawebinar.topjava.util.TimeUtil.isBetweenHalfOpen;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(13, 1), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals,
                                                            LocalTime startTime,
                                                            LocalTime endTime,
                                                            int caloriesPerDay) {
        List<UserMealWithExcess> result = new ArrayList<>();
        LocalDate tmpDate = null;
        boolean tmpExcess = false;
        for (UserMeal curMeal : meals) {
            LocalDate curDate = curMeal.getDateTime().toLocalDate();
            if (!curDate.equals(tmpDate)) {
                tmpDate = curDate;
                tmpExcess = isExcessOfDate(meals, curDate, caloriesPerDay);
            }
            if (isBetweenHalfOpen(curMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                result.add(mapUserMealToUserMealWithExcess(curMeal, tmpExcess));
            }
        }
        return result;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        return meals.stream()
                .filter(Objects::nonNull)
                .filter(userMeal -> isBetweenHalfOpen(userMeal.getDateTime().toLocalTime(), startTime, endTime))
                .map(userMeal -> mapUserMealToUserMealWithExcess(userMeal,
                        isExcessOfDate(meals, userMeal.getDateTime().toLocalDate(), caloriesPerDay)))
                .collect(Collectors.toList());
    }

    private static boolean isExcessOfDate(List<UserMeal> meals, LocalDate curDate, int caloriesPerDay) {
        int averageCalories = meals.stream()
                .filter(Objects::nonNull)
                .filter(userMeal -> curDate.equals(userMeal.getDateTime().toLocalDate()))
                .flatMapToInt(userMeal -> IntStream.of(userMeal.getCalories()))
                .sum();
        return averageCalories > caloriesPerDay;
    }
}