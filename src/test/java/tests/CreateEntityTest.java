package tests;

import helpers.BaseTest;
import helpers.CreateEntityHelper;
import helpers.Specifications;
import io.qameta.allure.Step;
import io.restassured.mapper.ObjectMapperType;
import org.junit.jupiter.api.*;
import pojo.Message;
import java.io.IOException;
import static io.restassured.RestAssured.given;

public class CreateEntityTest extends BaseTest {
    @Test
    @DisplayName("Создание сущности")
    @Step("Сущность создана")
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
        Assertions.assertEquals(CreateEntityHelper.getMessage().getAddition(), expectedMessage.getAddition());
        Assertions.assertEquals(CreateEntityHelper.getMessage().getVerified(), expectedMessage.getVerified());
        Assertions.assertEquals(CreateEntityHelper.getMessage().getImportant_numbers(), expectedMessage.getImportant_numbers());
    }
    @AfterAll
    public static void clean() {
        Specifications.deleteEntity(CreateEntityHelper.getEntityId());
    }
}