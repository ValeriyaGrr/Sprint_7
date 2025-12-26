package ru.praktikum.sprint_7.tests;

import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.sprint_7.clients.CourierClient;
import ru.praktikum.sprint_7.clients.LoginClient;
import ru.praktikum.sprint_7.models.Courier;

import static org.apache.http.HttpStatus.*;
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

        Courier courier = new Courier(login, "123456", "Name");
        courierClient.createCourier(courier)
                .then()
                .statusCode(SC_CREATED);

        Courier credentials = new Courier(login, "123456");
        Response loginResponse = loginClient.loginCourier(credentials);

        courierId = loginResponse.jsonPath().getInt("id");
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
        Courier credentials = new Courier(login, "123456");

        loginClient.loginCourier(credentials)
                .then()
                .statusCode(SC_OK)
                .body("id", notNullValue());
    }

    @Test
    @Description("Для авторизации нужны все поля")
    public void shouldReturnErrorIfLoginFieldMissing() {
        Courier credentials = new Courier(null, "123456");

        loginClient.loginCourier(credentials)
                .then()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @Description("Неверный пароль — ошибка")
    public void shouldReturnErrorIfWrongPassword() {
        Courier credentials = new Courier(login, "wrongpass");

        loginClient.loginCourier(credentials)
                .then()
                .statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @Description("Несуществующий логин — ошибка")
    public void shouldReturnErrorIfLoginNotFound() {
        Courier credentials = new Courier("nonexistent_login_999", "123456");

        loginClient.loginCourier(credentials)
                .then()
                .statusCode(SC_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }
}
