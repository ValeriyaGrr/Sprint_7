package ru.praktikum.sprint_7.tests;

import io.qameta.allure.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.sprint_7.clients.CourierClient;

import static org.hamcrest.Matchers.equalTo;

public class CourierApiTest {
    private CourierClient client;
    private String login;
    private int courierId;

    @Before
    public void setUp() {
        client = new CourierClient();
        login = "courier_" + System.currentTimeMillis();
    }

    @After
    public void tearDown() {
        if (courierId != 0) {
            client.deleteCourier(courierId);
        }
    }

    @Test
    @Description("Курьера можно создать")
    public void shouldCreateCourierSuccessfully() {
        String body = String.format(
                "{\"login\":\"%s\",\"password\":\"123456\",\"firstName\":\"Name\"}",
                login
        );
        client.createCourier(body)
                .then()
                .statusCode(201)
                .body("ok", equalTo(true));
    }

    @Test
    @Description("Нельзя создать курьера без логина")
    public void shouldReturnErrorIfLoginMissing() {
        String body = "{\"password\":\"123456\",\"firstName\":\"Name\"}";
        client.createCourier(body)
                .then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @Description("Нельзя создать курьера без пароля")
    public void shouldReturnErrorIfPasswordMissing() {
        String body = String.format("{\"login\":\"%s\",\"firstName\":\"Name\"}", login);
        client.createCourier(body)
                .then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @Description("Нельзя создать двух одинаковых курьеров")
    public void shouldReturnConflictIfDuplicateLogin() {

        String body1 = String.format(
                "{\"login\":\"%s\",\"password\":\"123456\",\"firstName\":\"Name\"}",
                login
        );
        client.createCourier(body1).then().statusCode(201);

        String body2 = String.format(
                "{\"login\":\"%s\",\"password\":\"654321\",\"firstName\":\"Other\"}",
                login
        );
        client.createCourier(body2)
                .then()
                .statusCode(409)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @Description("Успешный запрос возвращает {ok: true}")
    public void shouldReturnOkTrueOnSuccess() {
        String body = String.format(
                "{\"login\":\"%s\",\"password\":\"123456\",\"firstName\":\"Name\"}",
                login
        );
        client.createCourier(body)
                .then()
                .statusCode(201)
                .body("ok", equalTo(true));
    }
}