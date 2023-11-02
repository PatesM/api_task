package steps.asserts;

import static org.assertj.core.api.Java6Assertions.assertThat;

import models.negative_response.NegativeResponseForAllModels;

public class AssertNegativeResult {

    public static void assertionNegativeResult(NegativeResponseForAllModels response,
        Integer errorCode, String errorMessage, String errorDetails) {
        assertThat(response.getErrorCode()).isEqualTo(errorCode);
        assertThat(response.getErrorMessage()).isEqualTo(errorMessage);
        assertThat(response.getErrorDetails()).isEqualTo(errorDetails);
    }
}
