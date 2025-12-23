package ru.praktikum.sprint_7.clients;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CourierClient {
    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";

    static {
        RestAssured.baseURI = BASE_URL;
    }

    public Response createCourier(String body) {
        return given()
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .post("/api/v1/courier");
    }

    public Response deleteCourier(int courierId) {
        return given()
                .when()
                .delete("/api/v1/courier/" + courierId);
    }
}