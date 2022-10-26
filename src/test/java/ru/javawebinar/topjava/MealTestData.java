package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {

    public static final int NOT_FOUND = 10;
    public static final Meal USER_MEAL1 = new Meal(100009, LocalDateTime.of(2022, Month.OCTOBER, 26, 13, 46), "User обед", 1000);
    public static final Meal USER_MEAL2 = new Meal(100010, LocalDateTime.of(2022, Month.OCTOBER, 25, 13, 49), "User обед", 111);
    public static final Meal USER_MEAL3 = new Meal(100008, LocalDateTime.of(2022, Month.OCTOBER, 24, 9, 45), "User завтрак", 1000);
    public static final Meal USER_MEAL4 = new Meal(100007, LocalDateTime.of(2022, Month.JANUARY, 21, 19, 30), "User ужин", 500);
    public static final Meal USER_MEAL5 = new Meal(100006, LocalDateTime.of(2022, Month.JANUARY, 21, 12, 30), "User обед", 1000);
    public static final Meal USER_MEAL6 = new Meal(100005, LocalDateTime.of(2022, Month.JANUARY, 21, 9, 30), "User завтрак", 500);



    public static Meal getNew(){
        return new Meal(LocalDateTime.of(2022, Month.NOVEMBER, 26, 11, 11), "New user meal", 111);
    }

    public static void assertMatch(Meal actual, Meal expected){
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static Meal getUpdated() {
        return new Meal(100006, LocalDateTime.of(2022, Month.JANUARY, 21, 12, 32), "User Updated", 1001);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }
}
