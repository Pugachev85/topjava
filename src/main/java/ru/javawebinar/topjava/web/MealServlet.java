package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.InRamMealsRepository;
import ru.javawebinar.topjava.repository.MealsRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final int CALORIES_PER_DAY = 2000;
    private MealsRepository repository;

    @Override
    public void init() throws ServletException {
        super.init();
        repository = new InRamMealsRepository();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> params = request.getParameterMap();
        if (params.containsKey("new")) {
            request.getRequestDispatcher("/mealsEdit.jsp").forward(request, response);
        } else if (params.containsKey("update")) {
            request.setAttribute("melForUpdate", repository.findById(Integer.parseInt(params.get("update")[0])));
            request.getRequestDispatcher("/mealsEdit.jsp").forward(request, response);
        } else if (params.containsKey("delete")) {
            repository.delete(Integer.parseInt(params.get("delete")[0]));
            response.sendRedirect("meals");
        } else {
            log.debug("get URL /meals");
            request.setAttribute("mealsList", MealsUtil.filteredByStreams(repository.getAll(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY));
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Map<String, String[]> params = request.getParameterMap();
        Meal meal = new Meal(LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        if (params.containsKey("new")) {
            log.debug("Post URL /meals?new opened");
            log.debug("Post parameters: {}, {}, {}", request.getParameter("dateTime"),
                    request.getParameter("description"), request.getParameter("calories"));
            repository.add(meal);
        }
        if (params.containsKey("update")) {
            meal.setId(Integer.valueOf(params.get("update")[0]));
            log.info("Meal for Update request: {} ", request.getParameter("description"));
            repository.update(meal);
        }
        response.sendRedirect("meals");
    }
}
