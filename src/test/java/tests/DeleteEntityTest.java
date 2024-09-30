package tests;

import helpers.CreateEntityHelper;
import helpers.Specifications;
import io.qameta.allure.Step;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static io.restassured.RestAssured.given;


public class DeleteEntityTest {
    private static RequestSpecification requestSpecification;
    private static String entityId;

    @BeforeAll
    public static void setup() throws IOException {
        requestSpecification = Specifications.requestSpec();
        CreateEntityHelper.createEntitySetup();
    }

    @Test
    @DisplayName("Проверка удаления сущности")
    @Step("Сущность удалена")
    public void testCreateEntity() {
        given()
                .spec(requestSpecification)
                .when()
                .delete("/delete/" + CreateEntityHelper.getEntityId())
                .then()
                .statusCode(204);

        given()
                .spec(requestSpecification)
                .when()
                .log().all()
                .get("get/" + CreateEntityHelper.getEntityId())
                .then()
                .log().all()
                .statusCode(500); // Проверка статус-кода
    }
}
