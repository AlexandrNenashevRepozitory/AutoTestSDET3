package tests;

import helpers.BaseTest;
import helpers.CreateEntityHelper;
import helpers.Specifications;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import org.junit.jupiter.api.*;
import pojo.Message;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class PatchEntityTest extends BaseTest {
    @Test
    @DisplayName("Изменение сущности")
    @Step("Сущность изменена")
    public void updateUser() throws IOException {
        CreateEntityHelper.getMessage().setTitle("Заголовок сущности 1");
        CreateEntityHelper.getMessage().setVerified(false);

        given()
                .spec(Specifications.requestSpec())
                .contentType(ContentType.JSON)
                .body(CreateEntityHelper.getMessage())
                .when()
                .patch("/patch/" + CreateEntityHelper.getEntityId())
                .then()
                .log().all()
                .statusCode(204);

        Message expectedMessage = given()
                .spec(Specifications.requestSpec())
                .when()
                .log().all()
                .get("get/" + CreateEntityHelper.getEntityId())
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .as(Message.class, ObjectMapperType.GSON);

        Assertions.assertEquals(CreateEntityHelper.getMessage().getTitle(), expectedMessage.getTitle());
        Assertions.assertEquals(CreateEntityHelper.getMessage().getVerified(), expectedMessage.getVerified());
    }
    @AfterAll
    public static void clean() {
        Specifications.deleteEntity(CreateEntityHelper.getEntityId());
    }
}







