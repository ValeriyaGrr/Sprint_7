package ru.praktikum.sprint_7.clients;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderClient {
    static {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    public Response createOrder(String body) {
        return given()
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .post("/api/v1/orders");
    }

    public Response getOrdersList() {
        return given()
                .when()
                .get("/api/v1/orders");
    }
}