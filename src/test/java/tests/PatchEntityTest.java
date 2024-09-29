package tests;

import helpers.Specifications;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
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


public class PatchEntityTest {
    private static RequestSpecification requestSpecification;
    private static String entityId;

    static Message message;


    @BeforeAll
    public static void setup()  throws IOException {
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
    @DisplayName("Изменение сущности")
    @Step("Сущность изменена")
    @Order(2)
    public void updateUser() {

        // Создаем объект Message
        message.setTitle("Заголовок сущности (Update)");
        message.setVerified(false);


        given()
                .spec(requestSpecification)
                .contentType(ContentType.JSON)
                .body(message)
                .when()
                .patch("/patch/" + entityId)
                .then()
                .log().all()
                .statusCode(204);

        Specifications.deleteEntity(entityId);
    }
}







