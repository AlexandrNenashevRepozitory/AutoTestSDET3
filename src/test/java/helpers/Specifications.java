package helpers;

import com.sun.tools.javac.Main;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.Message;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static io.restassured.RestAssured.given;


public class Specifications {
    static String url;

    // Метод для создания спецификации запроса
    public static RequestSpecification requestSpec() throws IOException {
        Properties properties = new Properties();

        try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("env.properties")) {
            properties.load(inputStream);
            url = properties.getProperty("apiUrl");

            return new RequestSpecBuilder()
                    .setBaseUri(url)
                    .setContentType("application/json")
                    .log(LogDetail.ALL)
                    .build();
        }
    }

    public static String createEntity(Message message) throws IOException {
        // Выполняем POST-запрос на создание сущности
        Response response = given()
                .spec(requestSpec())
                .body(message)
                .when()
                .post("/create");

        response.then().statusCode(200);
        return response.getBody().asString();
    }

    public static void deleteEntity(String id) {
        given()
                .when()
                .delete(url + "/delete/" + id)
                .then()
                .statusCode(204);
    }
}