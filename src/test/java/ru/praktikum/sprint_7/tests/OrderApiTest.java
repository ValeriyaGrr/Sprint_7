package ru.praktikum.sprint_7.tests;

import io.qameta.allure.Description;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.praktikum.sprint_7.clients.OrderClient;
import ru.praktikum.sprint_7.models.Order;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderApiTest {

    private final List<String> colors;

    public OrderApiTest(List<String> colors) {
        this.colors = colors;
    }

    @Parameterized.Parameters(name = "Цвета: {0}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {List.of()},
                {List.of("BLACK")},
                {List.of("GREY")},
                {List.of("BLACK", "GREY")}
        });
    }

    @Test
    @Description("Можно создать заказ с любым набором цветов")
    public void shouldCreateOrderWithAnyColorVariant() {
        Order order = new Order(
                "Валерия",
                "Тестерова",
                "Ленина 1",
                4,
                "+78005553535",
                5,
                "2025-12-25",
                "звонить заранее",
                colors
        );

        new OrderClient().createOrder(order)
                .then()
                .statusCode(SC_CREATED)
                .body("track", notNullValue());
    }
}
