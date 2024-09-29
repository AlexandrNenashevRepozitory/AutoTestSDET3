package tests;

import helpers.Specifications;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import pojo.Addition;
import pojo.Message;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import static io.restassured.RestAssured.given;

public class PatchEntityTest {
    private static RequestSpecification requestSpecification;
    private static String entityId;
    private static Message message;
    private static Random random;

    @BeforeAll
    public static void setup() throws IOException {
        random = new Random();
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
    @DisplayName("Изменение сущности")
    @Step("Сущность изменена")
    public void updateUser() {

        message.setTitle("Заголовок сущности 1");
        message.setVerified(false);
        message.setImportant_numbers(List.of(random.nextInt(), random.nextInt(), random.nextInt()));

        given()
                .spec(requestSpecification)
                .contentType(ContentType.JSON)
                .body(message)
                .when()
                .patch("/patch/" + entityId)
                .then()
                .log().all()
                .statusCode(204);

        Message expectedMessage = given()
                .spec(requestSpecification)
                .when()
                .log().all()
                .get("get/" + entityId)
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .as(Message.class, ObjectMapperType.GSON);

        Assertions.assertEquals(message.getImportant_numbers(), expectedMessage.getImportant_numbers());
        Assertions.assertEquals(message.getTitle(), expectedMessage.getTitle());
        Assertions.assertEquals(message.getVerified(), expectedMessage.getVerified());
    }
    @AfterAll
    public static void clean() {
        Specifications.deleteEntity(entityId);
    }
}







