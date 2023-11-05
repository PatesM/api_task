package steps.request_steps;

import static io.restassured.RestAssured.given;
import static steps.specifications.RequestSpecifications.requestSpecification;

import configuration.Properties;
import models.add_new_author.SaveNewAuthorRequest;
import models.add_new_author.SaveNewAuthorResponse;
import models.save_new_book.SaveNewBookRequest;
import models.save_new_book.SaveNewBookResponse;

public class ApiMethods {

    public static SaveNewAuthorResponse saveNewAuthorApiMethod(SaveNewAuthorRequest authorRequest) {
        return given().spec(requestSpecification(Properties.POST_AUTHOR_URI))
            .body(authorRequest)
            .when()
            .post()
            .then()
            .assertThat()
            .statusCode(201)
            .extract().as(SaveNewAuthorResponse.class);
    }

    public static SaveNewBookResponse saveNewBookApiMethod(SaveNewBookRequest bookRequest) {
        return given().spec(requestSpecification(Properties.POST_BOOK_URI))
            .body(bookRequest)
            .when()
            .post()
            .then()
            .assertThat()
            .statusCode(201)
            .extract().as(SaveNewBookResponse.class);
    }
}
