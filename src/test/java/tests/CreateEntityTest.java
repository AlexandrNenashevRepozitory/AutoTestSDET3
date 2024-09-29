package tests;

import helpers.Specifications;
import io.qameta.allure.Step;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import pojo.Addition;
import pojo.Message;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import static io.restassured.RestAssured.given;


public class CreateEntityTest {
    private static RequestSpecification requestSpecification;
    private static String entityId;
    private static Message message;

    @BeforeAll
    public static void setup() throws IOException {
        Random random = new Random();
        requestSpecification = Specifications.requestSpec();

        Addition addition = Addition.builder()
                .additional_info("Дополнительные сведения")
                .build();

        message = Message.builder()
                .addition(addition)
                .important_numbers(Arrays.asList(random.nextInt(), random.nextInt(), random.nextInt()))
                .title("Заголовок сущности")
                .verified(true)
                .build();

        entityId = Specifications.createEntity(message);
    }

    @Test
    @DisplayName("Создание сущности")
    @Step("Сущность создана")
    public void testCreateEntity() {
        Message expectedMessage = given()
                .spec(requestSpecification)
                .when()
                .log().all()
                .get("get/" + entityId)
                .then()
                .log().all()
                .statusCode(200)  // Проверка статус-кода
                .extract()
                .as(Message.class, ObjectMapperType.GSON);

        Assertions.assertEquals(message.getTitle(), expectedMessage.getTitle());
        Assertions.assertEquals(message.getAddition(), expectedMessage.getAddition());
        Assertions.assertEquals(message.getVerified(), expectedMessage.getVerified());
        Assertions.assertEquals(message.getImportant_numbers(), expectedMessage.getImportant_numbers());
    }

    @AfterAll
    public static void clean() {
        Specifications.deleteEntity(entityId);
    }
}