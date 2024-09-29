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
import static org.hamcrest.Matchers.equalTo;

public class GetEntityTest {
    private static RequestSpecification requestSpecification;
    private static String entityId;

    static Message message;


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
        message = Message.builder()
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
    @DisplayName("Получение сущности")
    @Step("Сущность получена")
    @Order(2)
    public void testGetEntity() {

        given()
                .spec(requestSpecification)
                .when()
                .log().all()
                .get("/get/" + entityId)
                .then()
                .log().all()
                .statusCode(200)  // Проверка статус-кода
                .body("addition.additional_info", equalTo(message.getAddition().getAdditional_info()),
                        "important_numbers", equalTo(message.getImportant_numbers()),
                        "title", equalTo(message.getTitle()),
                        "verified", equalTo(message.getVerified()));

            Specifications.deleteEntity(entityId);
        }
}
