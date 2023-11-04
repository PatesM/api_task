package steps.request_steps;

import static io.restassured.RestAssured.given;
import static steps.specifications.RequestSpecifications.requestSpecification;

import configuration.Properties;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import models.add_new_author.SaveNewAuthorRequest;
import models.add_new_author.SaveNewAuthorResponse;
import models.authorize_user.AuthorizationRequest;
import models.authorize_user.AuthorizationResponse;
import models.save_new_book.SaveNewBookRequest;
import models.save_new_book.SaveNewBookResponse;

public class ApiMethods {

    public static AuthorizationResponse AuthorizationApiMethod() {
        AuthorizationRequest authorization = new AuthorizationRequest("test_log", "123qweasd");

        return given()
            .baseUri(Properties.BASE_URL)
            .basePath(Properties.GET_AUTH_URI)
            .contentType(ContentType.JSON)
            .filter(new RequestLoggingFilter())
            .filter(new ResponseLoggingFilter())
            .body(authorization)
            .when()
            .get()
            .then()
            .assertThat()
            .statusCode(200)
            .extract().as(AuthorizationResponse.class);
    }

    public static SaveNewAuthorResponse SaveNewAuthorApiMethod(
        AuthorizationResponse authorizationResponse, SaveNewAuthorRequest authorRequest) {
        return given().spec(requestSpecification(Properties.POST_AUTHOR_URI, authorizationResponse))
            .body(authorRequest)
            .when()
            .post()
            .then()
            .assertThat()
            .statusCode(201)
            .extract().as(SaveNewAuthorResponse.class);
    }

    public static SaveNewBookResponse SaveNewBookApiMethod(
        AuthorizationResponse authorizationResponse, SaveNewBookRequest bookRequest) {
        return given().spec(requestSpecification(Properties.POST_BOOK_URI, authorizationResponse))
            .body(bookRequest)
            .when()
            .post()
            .then()
            .assertThat()
            .statusCode(201)
            .extract().as(SaveNewBookResponse.class);
    }
}
