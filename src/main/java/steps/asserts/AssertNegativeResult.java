package steps.asserts;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.hamcrest.Matchers.is;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;
import models.negative_response.NegativeResponseForAllModels;

public class AssertNegativeResult {

    public static void assertionNegativeResult(NegativeResponseForAllModels response,
        Integer errorCode, String errorMessage, String errorDetails) {
        assertThat(response.getErrorCode()).isEqualTo(errorCode);
        assertThat(response.getErrorMessage()).isEqualTo(errorMessage);
        assertThat(response.getErrorDetails()).isEqualTo(errorDetails);
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
