package tests;

import helpers.BaseTest;
import helpers.CreateEntityHelper;
import helpers.Specifications;
import io.qameta.allure.Step;
import io.restassured.mapper.ObjectMapperType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.Message;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class GetEntityTest extends BaseTest {
    @Test
    @DisplayName("Получение сущности")
    @Step("Сущность получена")
    public void testCreateEntity() throws IOException {
        Message expectedMessage = given()
                .spec(Specifications.requestSpec())
                .when()
                .log().all()
                .get("get/" + CreateEntityHelper.getEntityId())
                .then()
                .log().all()
                .statusCode(200)  // Проверка статус-кода
                .extract()
                .as(Message.class, ObjectMapperType.GSON);

        Assertions.assertEquals(CreateEntityHelper.getMessage().getTitle(), expectedMessage.getTitle());
        Assertions.assertEquals(CreateEntityHelper.getMessage().getImportant_numbers(), expectedMessage.getImportant_numbers());
        Assertions.assertEquals(CreateEntityHelper.getMessage().getAddition(), expectedMessage.getAddition());
        Assertions.assertEquals(CreateEntityHelper.getMessage().getVerified(), expectedMessage.getVerified());
    }
    @AfterAll
    public static void clean() {
        Specifications.deleteEntity(CreateEntityHelper.getEntityId());
    }
}
