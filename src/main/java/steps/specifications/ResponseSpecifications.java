package steps.specifications;

import static org.hamcrest.Matchers.is;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;

public class ResponseSpecifications {

    public static ResponseSpecification responseSpecificationGet(int expectedStatusCode) {
        return new ResponseSpecBuilder()
            .expectStatusCode(expectedStatusCode)
            .build();
    }

    public static ResponseSpecification responseSpecificationPost(int expectedStatusCode,
        String bodyName, Integer expectedValue) {
        return new ResponseSpecBuilder()
            .expectStatusCode(expectedStatusCode)
            .expectBody(bodyName, is(expectedValue))
            .build();
    }
}
