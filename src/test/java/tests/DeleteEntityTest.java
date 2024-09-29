package tests;

import helpers.Specifications;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import pojo.Addition;
import pojo.Message;

import java.io.IOException;
import java.util.Arrays;

import static io.restassured.RestAssured.given;


public class DeleteEntityTest {

    private static RequestSpecification requestSpecification;

    private static String entityId;

    @BeforeAll
    public static void setup() throws IOException {
        requestSpecification = Specifications.requestSpec();
    }


    @Test
    @DisplayName("Создание сущности")
    @Step("Сущность создана")
    @Order(1)
    public void testCreateEntity() {

        // Создаем объект Addition
        Addition addition = Addition.builder()
                .additional_info("Дополнительные сведения")
                .build();

        // Создаем объект Message
        Message message = Message.builder()
                .addition(addition)
                .important_numbers(Arrays.asList(1, 2, 3))
                .title("Заголовок сущности")
                .verified(true)
                .build();

        // Выполняем POST-запрос на создание сущности
        Response response = given()
                .spec(requestSpecification)
                .body(message)
                .when()
                .post("/create");

        response.then().statusCode(200);  // Проверка статус-кода

        // Извлекаем ID созданной сущности и сохраняем в статическую переменную
        entityId = response.getBody().asString();
    }


    @Test
    @DisplayName("Удаление сущности")
    @Step("Сущность удалена")
    @Order(2)
    public void testDelEntity() {
        given()
                .spec(requestSpecification)
                .when()
                .delete("/delete/" + entityId)
                .then()
                .statusCode(204);
    }
}