package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
@RequestMapping(value = "/meals")
public class JspMealController {

    private static final Logger log = LoggerFactory.getLogger(JspMealController.class);

    @Autowired
    private MealService service;

    @GetMapping()
    public String getMeals(Model model, HttpServletRequest request) {
        log.info("meals");
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
        List<Meal> mealsDateFiltered = service.getBetweenInclusive(startDate, endDate, SecurityUtil.authUserId());

        model.addAttribute("meals",
                MealsUtil.getFilteredTos(mealsDateFiltered, SecurityUtil.authUserCaloriesPerDay(), startTime, endTime));
        return "meals";
    }

    @GetMapping(params = "action=update")
    public String getForUpdate(Model model, HttpServletRequest request) {
        log.info("get meal: {} for update", request.getParameter("id"));
        model.addAttribute("meal", service.get(Integer.parseInt(request.getParameter("id")), SecurityUtil.authUserId()));
        return "mealForm";
    }

    @GetMapping(params = "action=create")
    public String getCreateForm(Model model) {
        log.info("get create form");
        Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @PostMapping
    public String update(HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        Meal meal = new Meal(request.getParameter("id").isEmpty() ? null : Integer.parseInt(request.getParameter("id")),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        if (meal.isNew()) {
            log.info("create meal: {}", meal);
            service.create(meal, SecurityUtil.authUserId());
        } else {
            log.info("update meal: {}", meal);
            service.update(meal, SecurityUtil.authUserId());
        }

        return "redirect:meals";
    }

    @GetMapping(params = "action=delete")
    public String delete(HttpServletRequest request) {
        log.info("delete meal Id: {}", request.getParameter("id"));
        service.delete(Integer.parseInt(request.getParameter("id")), SecurityUtil.authUserId());
        return "redirect:meals";
    }
}
