package steps.asserts;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;
import models.negative_response.NegativeResponseForAllModels;

public class AssertNegativeResult {

    public static void assertionNegativeResult(NegativeResponseForAllModels response,
        Integer errorCode, String errorMessage, String errorDetails) {
        assertEquals(response.getErrorCode(), errorCode);
        assertEquals(response.getErrorMessage(), errorMessage);
        assertEquals(response.getErrorDetails(), errorDetails);
    }

    public static ResponseSpecification responseSpecificationNegativeResult(int expectedStatusCode,
        Integer errorCode, String errorMessage, String errorDetails) {
        return new ResponseSpecBuilder()
            .expectStatusCode(expectedStatusCode)
            .expectBody("errorCode", is(errorCode))
            .expectBody("errorMessage", is(errorMessage))
            .expectBody("errorDetails", is(errorDetails))
            .build();
    }
}
