package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.service.ImplMealService;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);
    MealService mealService = new ImplMealService();

    final int caloriesPerDay = 2000;
    final LocalTime startTime = LocalTime.of(0, 0);
    final LocalTime endTime = LocalTime.of(23, 59);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("get URL /topjava/meals");

        request.setAttribute("mealsList", MealsUtil.filteredByStreams(mealService.getAllMeals(), startTime, endTime, caloriesPerDay));
        request.getRequestDispatcher("/meals.jsp").forward(request, response);

    }
}
