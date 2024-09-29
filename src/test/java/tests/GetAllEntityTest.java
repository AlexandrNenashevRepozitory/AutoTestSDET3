package tests;

import helpers.Specifications;
import io.qameta.allure.Step;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static io.restassured.RestAssured.given;

public class GetAllEntityTest {
    private static RequestSpecification requestSpecification;

    @BeforeAll
    public static void setup() throws IOException {
        requestSpecification = Specifications.requestSpec();
    }

    @Test
    @DisplayName("Получение всех сущностей")
    @Step("Сущности получены")
    public void testGetAllEntity() {
        given()
                .spec(requestSpecification)
                .when()
                .log().all()
                .get("/getAll")
                .then()
                .log().all()
                .statusCode(200);
    }
}

