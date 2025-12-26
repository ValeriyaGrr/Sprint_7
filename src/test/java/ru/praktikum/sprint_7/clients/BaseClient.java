package ru.praktikum.sprint_7.clients;

import io.restassured.RestAssured;

public abstract class BaseClient {

    protected static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";

    static {
        RestAssured.baseURI = BASE_URL;
    }
}
