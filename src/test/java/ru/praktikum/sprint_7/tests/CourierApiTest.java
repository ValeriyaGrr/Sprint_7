package ru.praktikum.sprint_7.tests;

import io.qameta.allure.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.sprint_7.clients.CourierClient;
import ru.praktikum.sprint_7.clients.LoginClient;
import ru.praktikum.sprint_7.models.Courier;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CourierApiTest {

    private CourierClient courierClient;
    private LoginClient loginClient;
    private String login;
    private int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        loginClient = new LoginClient();
        login = "courier_" + System.currentTimeMillis();
    }

    @After
    public void tearDown() {
        if (courierId != 0) {
            courierClient.deleteCourier(courierId);
        }
    }

    @Test
    @Description("Курьера можно создать")
    public void shouldCreateCourierSuccessfully() {
        Courier courier = new Courier(login, "123456", "Name");

        courierClient.createCourier(courier)
                .then()
                .statusCode(SC_CREATED)
                .body("ok", equalTo(true));

        courierId = loginClient.loginCourier(courier)
                .then()
                .statusCode(SC_OK)
                .body("id", notNullValue())
                .extract()
                .path("id");
    }

    @Test
    @Description("Нельзя создать курьера без логина")
    public void shouldReturnErrorIfLoginMissing() {
        Courier courier = new Courier(null, "123456", "Name");

        courierClient.createCourier(courier)
                .then()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @Description("Нельзя создать курьера без пароля")
    public void shouldReturnErrorIfPasswordMissing() {
        Courier courier = new Courier(login, null, "Name");

        courierClient.createCourier(courier)
                .then()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @Description("Нельзя создать двух курьеров с одинаковым логином")
    public void shouldReturnConflictIfDuplicateLogin() {
        Courier firstCourier = new Courier(login, "123456", "Name");
        Courier duplicateCourier = new Courier(login, "654321", "Other");

        courierClient.createCourier(firstCourier)
                .then()
                .statusCode(SC_CREATED);

        courierClient.createCourier(duplicateCourier)
                .then()
                .statusCode(SC_CONFLICT)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }
}
