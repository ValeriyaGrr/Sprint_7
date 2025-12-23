package ru.praktikum.sprint_7.tests;

import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.sprint_7.clients.CourierClient;
import ru.praktikum.sprint_7.clients.LoginClient;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LoginApiTest {
    private CourierClient courierClient;
    private LoginClient loginClient;
    private String login;
    private int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        loginClient = new LoginClient();
        login = "courier_" + System.currentTimeMillis();

        String body = String.format(
                "{\"login\":\"%s\",\"password\":\"123456\",\"firstName\":\"Name\"}",
                login
        );
        courierClient.createCourier(body).then().statusCode(201);
    }

    @After
    public void tearDown() {
        if (courierId != 0) {
            courierClient.deleteCourier(courierId);
        }
    }

    @Test
    @Description("Курьер может авторизоваться")
    public void shouldLoginSuccessfully() {
        String body = String.format(
                "{\"login\":\"%s\",\"password\":\"123456\"}",
                login
        );
        Response response = loginClient.loginCourier(body);
        courierId = response.jsonPath().getInt("id");

        response.then()
                .statusCode(200)
                .body("id", notNullValue());
    }

    @Test
    @Description("Для авторизации нужны все поля")
    public void shouldReturnErrorIfLoginFieldMissing() {
        String body = "{\"password\":\"123456\"}";
        loginClient.loginCourier(body)
                .then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @Description("Неверный пароль — ошибка 404")
    public void shouldReturn404IfWrongPassword() {
        String body = String.format(
                "{\"login\":\"%s\",\"password\":\"wrongpass\"}",
                login
        );
        loginClient.loginCourier(body)
                .then()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @Description("Несуществующий логин — ошибка 404")
    public void shouldReturn404IfLoginNotFound() {
        String body = "{\"login\":\"nonexistent_login_999\",\"password\":\"123456\"}";
        loginClient.loginCourier(body)
                .then()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }
}