package steps.specifications;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;

public class ResponseSpecifications {

    public static ResponseSpecification responseSpecificationGet() {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();
    }

    public static ResponseSpecification responseSpecificationPost() {
        return new ResponseSpecBuilder()
                .expectStatusCode(201)
                .build();
    }
}
