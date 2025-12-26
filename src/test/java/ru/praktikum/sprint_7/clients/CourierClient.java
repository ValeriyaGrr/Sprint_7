package ru.praktikum.sprint_7.clients;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.praktikum.sprint_7.models.Courier;

import static io.restassured.RestAssured.given;

public class CourierClient extends BaseClient {

    private static final String COURIER_PATH = "/api/v1/courier";

    @Step("Создание курьера")
    public Response createCourier(Courier courier) {
        return given()
                .header("Content-Type", "application/json")
                .body(courier)
                .post(COURIER_PATH);
    }

    @Step("Удаление курьера по id")
    public Response deleteCourier(int courierId) {
        return given()
                .delete(COURIER_PATH + "/" + courierId);
    }
}
