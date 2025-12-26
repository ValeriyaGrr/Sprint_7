package ru.praktikum.sprint_7.clients;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.praktikum.sprint_7.models.Courier;

import static io.restassured.RestAssured.given;

public class LoginClient extends BaseClient {

    private static final String LOGIN_PATH = "/api/v1/courier/login";

    @Step("Логин курьера")
    public Response loginCourier(Courier courier) {
        return given()
                .header("Content-Type", "application/json")
                .body(courier)
                .post(LOGIN_PATH);
    }
}
