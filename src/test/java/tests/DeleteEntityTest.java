package tests;

import helpers.Specifications;
import io.qameta.allure.Step;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.Addition;
import pojo.Message;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import static io.restassured.RestAssured.given;


public class DeleteEntityTest {
    private static RequestSpecification requestSpecification;
    private static String entityId;

    @BeforeAll
    public static void setup() throws IOException {
        Random random = new Random();
        requestSpecification = Specifications.requestSpec();

        Addition addition = Addition.builder()
                .additional_info("Дополнительные сведения")
                .build();

        Message message = Message.builder()
                .addition(addition)
                .important_numbers(Arrays.asList(random.nextInt(), random.nextInt(), random.nextInt()))
                .title("Заголовок сущности")
                .verified(true)
                .build();

        entityId = Specifications.createEntity(message);
    }

    @Test
    @DisplayName("Проверка удаления сущности")
    @Step("Сущность удалена")
    public void testCreateEntity() {
        given()
                .spec(requestSpecification)
                .when()
                .delete("/delete/" + entityId)
                .then()
                .statusCode(204);

        given()
                .spec(requestSpecification)
                .when()
                .log().all()
                .get("get/" + entityId)
                .then()
                .log().all()
                .statusCode(500); // Проверка статус-кода
    }
}
