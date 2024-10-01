package tests;

import helpers.BaseTest;
import helpers.CreateEntityHelper;
import helpers.Specifications;
import io.qameta.allure.Step;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static io.restassured.RestAssured.given;


public class DeleteEntityTest extends BaseTest  {
    @Test
    @DisplayName("Проверка удаления сущности")
    @Step("Сущность удалена")
    public void testCreateEntity() throws IOException {
        given()
                .spec(Specifications.requestSpec())
                .when()
                .delete("/delete/" + CreateEntityHelper.getEntityId())
                .then()
                .statusCode(204);

        given()
                .spec(Specifications.requestSpec())
                .when()
                .log().all()
                .get("get/" + CreateEntityHelper.getEntityId())
                .then()
                .log().all()
                .statusCode(500); // Проверка статус-кода
    }
}
