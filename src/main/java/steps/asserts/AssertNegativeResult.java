package steps.asserts;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;
import models.negative_response.NegativeResponseForAllModels;

public class AssertNegativeResult {

    public void assertionNegativeResult(NegativeResponseForAllModels response,
        Integer errorCode, String errorMessage, String errorDetails) {
        assertEquals(response.getErrorCode(), errorCode);
        assertEquals(response.getErrorMessage(), errorMessage);
        assertEquals(response.getErrorDetails(), errorDetails);
    }

    public ResponseSpecification responseSpecificationNegativeResult(int expectedStatusCode) {
        return new ResponseSpecBuilder()
            .expectStatusCode(expectedStatusCode)
            .build();
    }
}
