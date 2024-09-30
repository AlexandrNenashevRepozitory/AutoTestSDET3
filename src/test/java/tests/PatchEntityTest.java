package tests;

import helpers.CreateEntityHelper;
import helpers.Specifications;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import pojo.Message;

import java.io.IOException;
import java.util.Random;

import static io.restassured.RestAssured.given;

public class PatchEntityTest {
    private static RequestSpecification requestSpecification;
    private static Random random;

    @BeforeAll
    public static void setup() throws IOException {
        requestSpecification = Specifications.requestSpec();
        CreateEntityHelper.createEntitySetup();
    }

    @Test
    @DisplayName("Изменение сущности")
    @Step("Сущность изменена")
    public void updateUser() {

        CreateEntityHelper.getMessage().setTitle("Заголовок сущности 1");
        CreateEntityHelper.getMessage().setVerified(false);

        given()
                .spec(requestSpecification)
                .contentType(ContentType.JSON)
                .body(CreateEntityHelper.getMessage())
                .when()
                .patch("/patch/" + CreateEntityHelper.getEntityId())
                .then()
                .log().all()
                .statusCode(204);

        Message expectedMessage = given()
                .spec(requestSpecification)
                .when()
                .log().all()
                .get("get/" + CreateEntityHelper.getEntityId())
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .as(Message.class, ObjectMapperType.GSON);

        Assertions.assertEquals(CreateEntityHelper.getMessage().getImportant_numbers(), expectedMessage.getImportant_numbers());
        Assertions.assertEquals(CreateEntityHelper.getMessage().getTitle(), expectedMessage.getTitle());
        Assertions.assertEquals(CreateEntityHelper.getMessage().getVerified(), expectedMessage.getVerified());
    }
    @AfterAll
    public static void clean() {
        Specifications.deleteEntity(CreateEntityHelper.getEntityId());
    }
}







