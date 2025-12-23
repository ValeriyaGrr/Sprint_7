package ru.praktikum.sprint_7.clients;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class LoginClient {
    public Response loginCourier(String body) {
        return given()
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .post("/api/v1/courier/login");
    }
}