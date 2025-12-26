package ru.praktikum.sprint_7.tests;

import io.qameta.allure.Description;
import org.junit.Test;
import ru.praktikum.sprint_7.clients.OrderClient;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.*;

public class OrdersListTest {

    @Test
    @Description("GET /orders возвращает список заказов")
    public void shouldReturnOrdersList() {
        new OrderClient().getOrdersList()
                .then()
                .statusCode(SC_OK)
                .body("orders", notNullValue())
                .body("orders", instanceOf(java.util.ArrayList.class));
    }
}
