package steps.asserts;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;
import models.negative_response.NegativeResponseForAllModels;

public class AssertNegativeResult {

    public void assertionNegativeResultForSaveNewBook(NegativeResponseForAllModels response,
        String errorDetails) {
        assertEquals(response.getErrorCode(), 1001);
        assertEquals(response.getErrorMessage(), "Валидация не пройдена");
        assertEquals(response.getErrorDetails(), errorDetails);
    }

    public void assertionNegativeResultForGetAllAuthorBooks(NegativeResponseForAllModels response,
        Integer errorCode, String errorDetails) {
        assertEquals(response.getErrorCode(), errorCode);
        assertEquals(response.getErrorMessage(), "Валидация не пройдена");
        assertEquals(response.getErrorDetails(), errorDetails);
    }

    public ResponseSpecification responseSpecificationNegativeResult(int expectedStatusCode) {
        return new ResponseSpecBuilder()
            .expectStatusCode(expectedStatusCode)
            .build();
    }
}
