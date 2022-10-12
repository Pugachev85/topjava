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
    private final LocalTime startTime = LocalTime.MIN;
    private final LocalTime endTime = LocalTime.MAX;
    private final MealsRepository repository = new InRamMealsRepository();

    @Override
    public void init() throws ServletException {
        super.init();
        repository.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        repository.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        repository.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        repository.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        repository.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        repository.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        repository.add(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Map<String, String[]> params = request.getParameterMap();
        if (params.containsKey("new")) {
            request.getRequestDispatcher("/mealsEdit.jsp").forward(request, response);
        } else if (params.containsKey("update")) {
            request.setAttribute("melForUpdate", repository.findById(Integer.valueOf(params.get("update")[0])));
            request.getRequestDispatcher("/mealsEdit.jsp").forward(request, response);
        } else if (params.containsKey("delete")) {
            repository.delete(Integer.valueOf(params.get("delete")[0]));
            response.sendRedirect("meals");
        } else {
            log.debug("get URL /meals");
            request.setAttribute("mealsList", MealsUtil.filteredByStreams(repository.getAll(), startTime, endTime, CALORIES_PER_DAY));
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Map<String, String[]> params = request.getParameterMap();
        if (params.containsKey("new")) {
            log.debug("Post URL /meals?new opened");
            log.debug("Post parameters: " + request.getParameter("dateTime") + ", "
                    + request.getParameter("description") + ", "
                    + request.getParameter("calories"));
            repository.add(new Meal(LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"),
                    Integer.parseInt(request.getParameter("calories"))));
        }
        if (params.containsKey("update")) {
            Meal forUpdate = new Meal(LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"),
                    Integer.parseInt(request.getParameter("calories")));
            forUpdate.setId(Integer.valueOf(params.get("update")[0]));
            log.debug("Meal for Update request: " + request.getParameter("description"));
            repository.update(forUpdate);
        }
        response.sendRedirect("meals");
    }
}
