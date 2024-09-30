package helpers;

import io.restassured.specification.RequestSpecification;
import lombok.Getter;
import lombok.Setter;
import pojo.Addition;
import pojo.Message;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class CreateEntityHelper {
    public static RequestSpecification requestSpecification;

    @Setter
    @Getter
    private static String entityId;

    @Setter
    @Getter
    private static Message message;
        public static void createEntitySetup() throws IOException {
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
}
