package tests;

import helpers.CreateEntityHelper;
import helpers.Specifications;
import io.qameta.allure.Step;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import pojo.Message;
import java.io.IOException;
import static io.restassured.RestAssured.given;


public class CreateEntityTest {
    private static RequestSpecification requestSpecification;

    @BeforeAll
    public static void setup() throws IOException {
        requestSpecification = Specifications.requestSpec();
        CreateEntityHelper.createEntitySetup();

    }

    @Test
    @DisplayName("Создание сущности")
    @Step("Сущность создана")
    public void testCreateEntity() {
        Message expectedMessage = given()
                .spec(requestSpecification)
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