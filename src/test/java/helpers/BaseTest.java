package helpers;

import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import java.io.IOException;

public class BaseTest {
    @BeforeAll
    public static void setup() throws IOException {
        RequestSpecification requestSpecification = Specifications.requestSpec();
        CreateEntityHelper.createEntitySetup();
    }
}