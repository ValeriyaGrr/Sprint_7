package ru.praktikum.sprint_7.tests;

import io.qameta.allure.Description;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.praktikum.sprint_7.clients.OrderClient;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderApiTest {
    private final String colorsJson;

    public OrderApiTest(String colorsJson) {
        this.colorsJson = colorsJson;
    }

    @Parameterized.Parameters(name = "Цвета: {0}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"[]"},
                {"[\"BLACK\"]"},
                {"[\"GREY\"]"},
                {"[\"BLACK\",\"GREY\"]"}
        });
    }

    @Test
    @Description("Можно создать заказ с любым набором цветов")
    public void shouldCreateOrderWithAnyColorVariant() {
        String body = "{\n" +
                "  \"firstName\": \"Валерия\",\n" +
                "  \"lastName\": \"Тестерова\",\n" +
                "  \"address\": \"Ленина 1\",\n" +
                "  \"metroStation\": 4,\n" +
                "  \"phone\": \"+78005553535\",\n" +
                "  \"rentTime\": 5,\n" +
                "  \"deliveryDate\": \"2025-12-25\",\n" +
                "  \"comment\": \"звонить заранее\",\n" +
                "  \"color\": " + colorsJson + "\n" +
                "}";
        new OrderClient().createOrder(body)
                .then()
                .statusCode(201)
                .body("track", notNullValue());
    }
}