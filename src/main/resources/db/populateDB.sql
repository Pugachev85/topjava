DELETE
FROM meals;
DELETE
FROM user_roles;
DELETE
FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (date_time, description, calories, user_id)
VALUES ('2022-01-11 14:00:00', 'Админ ланч', 510, 100001),
       ('2022-01-11 21:00:00', 'Админ ужин', 1500, 100001),
       ('2022-01-21 09:30:00', 'User завтрак', 500, 100000),
       ('2022-01-21 12:30:00', 'User обед', 1000, 100000),
       ('2022-01-21 19:30:00', 'User ужин', 500, 100000),
       ('2022-10-24 09:45:00', 'User завтрак', 1000, 100000),
       ('2022-10-26 13:46:00', 'User обед', 1000, 100000),
       ('2022-10-25 13:49:00', 'User обед', 111, 100000);
