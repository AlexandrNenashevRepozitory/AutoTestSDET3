package tests;

import helpers.BaseTest;
import helpers.Specifications;
import io.qameta.allure.Step;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static io.restassured.RestAssured.given;

public class GetAllEntityTest extends BaseTest {
    @Test
    @DisplayName("Получение всех сущностей")
    @Step("Сущности получены")
    public void testGetAllEntity() throws IOException {
        given()
                .spec(Specifications.requestSpec())
                .when()
                .log().all()
                .get("/getAll")
                .then()
                .log().all()
                .statusCode(200);
    }
}

