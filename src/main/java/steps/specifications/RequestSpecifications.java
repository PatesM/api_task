package steps.specifications;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

import configuration.Properties;
import entity.AuthorTable;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import java.util.List;
import models.add_new_author.SaveNewAuthorRequest;
import models.add_new_author.SaveNewAuthorResponse;
import models.authorize_user.AuthorizationResponse;
import models.get_all_author_books.GetAllAuthorBooksResponse;
import models.get_all_author_books_xml.GetAllAuthorBooksXmlRequest;
import models.get_all_author_books_xml.GetAllAuthorBooksXmlResponse;
import models.negative_response.NegativeResponseForAllModels;
import models.save_new_book.SaveNewBookRequest;
import models.save_new_book.SaveNewBookResponse;
import steps.asserts.AssertNegativeResult;

public class RequestSpecifications {

    private static final AssertNegativeResult assertNegativeResult = new AssertNegativeResult();

    public static RequestSpecification requestSpecification(String basePath,
        AuthorizationResponse authorizationResponse) {
        return new RequestSpecBuilder()
            .setBaseUri(Properties.BASE_URL)
            .setBasePath(basePath)
            .setContentType(ContentType.JSON)
            .addHeader("Authorization", "Bearer " + authorizationResponse.getJwtToken())
            .addFilter(new RequestLoggingFilter())
            .addFilter(new ResponseLoggingFilter())
            .build();
    }

    public static RequestSpecification requestSpecificationXml(String basePath,
        AuthorizationResponse authorizationResponse) {
        return new RequestSpecBuilder()
            .setBaseUri(Properties.BASE_URL)
            .setBasePath(basePath)
            .setContentType(ContentType.XML)
            .addHeader("Authorization", "Bearer " + authorizationResponse.getJwtToken())
            .addFilter(new RequestLoggingFilter())
            .addFilter(new ResponseLoggingFilter())
            .build();
    }

    public static SaveNewAuthorResponse requestSpecificationSaveNewAuthor(
        AuthorizationResponse authorizationResponse, SaveNewAuthorRequest authorRequest,
        int expectedStatusCode, String bodyName) {

        return given().spec(requestSpecification(Properties.POST_AUTHOR_URI, authorizationResponse))
            .body(authorRequest)
            .when()
            .post()
            .then()
            .assertThat()
            .body(containsString(bodyName))
            .spec(ResponseSpecifications.responseSpecificationPost(expectedStatusCode))
            .extract().as(SaveNewAuthorResponse.class);
    }

    public static SaveNewBookResponse requestSpecificationSaveNewBookPositiveResult(
        AuthorizationResponse authorizationResponse, String bookTitle, Long authorId,
        int expectedStatusCode, String bodyName) {
        AuthorTable author = new AuthorTable(authorId);
        SaveNewBookRequest book = new SaveNewBookRequest(bookTitle, author);

        return given().spec(requestSpecification(Properties.POST_BOOK_URI, authorizationResponse))
            .body(book)
            .when()
            .post()
            .then()
            .assertThat()
            .body(containsString(bodyName))
            .spec(ResponseSpecifications.responseSpecificationPost(expectedStatusCode))
            .extract().as(SaveNewBookResponse.class);
    }

    public static NegativeResponseForAllModels requestSpecificationSaveNewBookNegativeResult(
        AuthorizationResponse authorizationResponse, String bookTitle, Long authorId,
        int expectedStatusCode) {
        AuthorTable author = new AuthorTable(authorId);
        SaveNewBookRequest book = new SaveNewBookRequest(bookTitle, author);

        return given().spec(requestSpecification(Properties.POST_BOOK_URI, authorizationResponse))
            .body(book)
            .when()
            .post()
            .then()
            .spec(
                assertNegativeResult.responseSpecificationNegativeResult(expectedStatusCode))
            .extract().as(NegativeResponseForAllModels.class);
    }

    public static List<GetAllAuthorBooksResponse> requestSpecificationGetAllAuthorBooksPositiveResult(
        AuthorizationResponse authorizationResponse, String authorId, int expectedStatusCode) {
        return given().spec(requestSpecification(Properties.GET_BOOKS_URI, authorizationResponse))
            .pathParam("id", authorId)
            .when()
            .get()
            .then()
            .spec(ResponseSpecifications.responseSpecificationGet(expectedStatusCode))
            .extract().jsonPath().getList(".", GetAllAuthorBooksResponse.class);
    }

    public static NegativeResponseForAllModels requestSpecificationGetAllAuthorBooksNegativeResult(
        AuthorizationResponse authorizationResponse, String authorId, int expectedStatusCode) {
        return given().spec(requestSpecification(Properties.GET_BOOKS_URI, authorizationResponse))
            .pathParam("id", authorId)
            .when()
            .get()
            .then()
            .spec(ResponseSpecifications.responseSpecificationGet(expectedStatusCode))
            .extract().as(NegativeResponseForAllModels.class);
    }

    public static GetAllAuthorBooksXmlResponse requestSpecificationGetAllAuthorBooksXmlPositiveResult(
        Integer authorId, AuthorizationResponse authorizationResponse, int expectedStatusCode) {
        GetAllAuthorBooksXmlRequest author = new GetAllAuthorBooksXmlRequest();
        author.setAuthorId(authorId);

        return given().spec(
                requestSpecificationXml(Properties.POST_BOOKS_XML_URI, authorizationResponse))
            .body(author)
            .when()
            .post()
            .then()
            .spec(ResponseSpecifications.responseSpecificationGet(expectedStatusCode))
            .extract().as(GetAllAuthorBooksXmlResponse.class);
    }

    public static NegativeResponseForAllModels requestSpecificationGetAllAuthorBooksXmlNegativeResult(
        AuthorizationResponse authorizationResponse, String authorId, int expectedStatusCode) {
        return given().spec(requestSpecification(Properties.GET_BOOKS_URI, authorizationResponse))
            .pathParam("id", authorId)
            .when()
            .get()
            .then()
            .spec(ResponseSpecifications.responseSpecificationGet(expectedStatusCode))
            .extract().as(NegativeResponseForAllModels.class);
    }
}
