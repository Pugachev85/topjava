package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDateTime;
import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management (ARM)
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "Andrey", "eeee@mail.ru", "password", Role.ADMIN));
            adminUserController.create(new User(null, "Andrey", "ffff@mail.ru", "password", Role.ADMIN));
            adminUserController.create(new User(null, "Andrey", "aaaa@mail.ru", "password", Role.ADMIN));
            adminUserController.create(new User(null, "Igor", "eeee@mail.ru", "password", Role.ADMIN));
            adminUserController.create(new User(null, "Igor", "ffff@mail.ru", "password", Role.ADMIN));
            adminUserController.create(new User(null, "Igor", "aaaa@mail.ru", "password", Role.ADMIN));
            System.out.println(adminUserController.get(5));
            adminUserController.update(new User(5, "Igor111", "a1a1a1a@mail.ru", "password", Role.ADMIN), 5);
            adminUserController.get(11);
            System.out.println(adminUserController.getAll());
            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            mealRestController.create(new Meal(LocalDateTime.now(), "password", 100));
            System.out.println(mealRestController.getAll());

        }
    }
}
