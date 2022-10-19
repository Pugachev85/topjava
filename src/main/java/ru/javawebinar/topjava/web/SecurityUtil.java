package ru.javawebinar.topjava.web;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

public class SecurityUtil {
    private static Integer authId = 1;

    public static int authUserId() {
        return authId;
    }

    public static void setAuthUserId(Integer authUserId) {
        authId = authUserId;
    }

    public static int authUserCaloriesPerDay() {
        return DEFAULT_CALORIES_PER_DAY;
    }
}