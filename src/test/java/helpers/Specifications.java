package helpers;

import com.sun.tools.javac.Main;
import io.restassured.builder.RequestSpecBuilder;

import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;


public class Specifications {
    static String url;
    // Метод для создания спецификации запроса
    public static RequestSpecification requestSpec() throws IOException {
        Properties properties = new Properties();

        try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("env.properties")) {
            properties.load(inputStream);
            url = properties.getProperty("apiUrl");

            return new RequestSpecBuilder()
                    .setBaseUri(url)  // Базовый URL
                    .setContentType("application/json")  // Установим тип контента JSON
                    .log(LogDetail.ALL)  // Логируем все детали запроса
                    .build();
        }
    }

    public static void deleteEntity(String id){
        given()
                .when()
                .delete(url + "/delete/" + id)
                .then()
                .statusCode(204);
    }
}