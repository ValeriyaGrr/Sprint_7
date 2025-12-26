package ru.praktikum.sprint_7.clients;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.praktikum.sprint_7.models.Order;

import static io.restassured.RestAssured.given;

public class OrderClient extends BaseClient {

    private static final String ORDERS_PATH = "/api/v1/orders";

    @Step("Создание заказа")
    public Response createOrder(Order order) {
        return given()
                .header("Content-Type", "application/json")
                .body(order)
                .post(ORDERS_PATH);
    }

    @Step("Получение списка заказов")
    public Response getOrdersList() {
        return given()
                .get(ORDERS_PATH);
    }
}
