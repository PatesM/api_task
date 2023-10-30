package steps.specifications;

import configuration.Properties;
import entity.AuthorTable;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import models.add_new_author.SaveNewAuthorRequest;
import models.add_new_author.SaveNewAuthorResponse;
import models.get_all_author_books.GetAllAuthorBooksResponse;
import models.get_all_author_books_xml.GetAllAuthorBooksXmlRequest;
import models.get_all_author_books_xml.GetAllAuthorBooksXmlResponse;
import models.save_new_book.SaveNewBookRequest;
import models.save_new_book.SaveNewBookResponse;

import java.util.List;

import static io.restassured.RestAssured.given;

public class RequestSpecifications {

    public static RequestSpecification requestSpecification() {
        return new RequestSpecBuilder()
                .setBaseUri(Properties.BASE_URL)
                .setContentType(ContentType.JSON)
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build();
    }

    public static RequestSpecification requestSpecificationXml() {
        return new RequestSpecBuilder()
                .setBaseUri(Properties.BASE_URL)
                .setContentType(ContentType.XML)
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build();
    }

    public static SaveNewAuthorResponse requestSpecificationSaveNewAuthor(String firstName, String familyName) {
        SaveNewAuthorRequest author = new SaveNewAuthorRequest(firstName, familyName);

        return given().spec(requestSpecification())
                .body(author)
                .when()
                .post(Properties.POST_AUTHOR_URI)
                .then()
                .spec(ResponseSpecifications.responseSpecificationPost())
                .extract().as(SaveNewAuthorResponse.class);
    }

    public static SaveNewBookResponse requestSpecificationSaveNewBook(String bookTitle, long authorId) {
        AuthorTable author = new AuthorTable(authorId);
        SaveNewBookRequest book = new SaveNewBookRequest(bookTitle, author);

        return given().spec(requestSpecification())
                .body(book)
                .when()
                .post(Properties.POST_BOOK_URI)
                .then()
                .spec(ResponseSpecifications.responseSpecificationPost())
                .extract().as(SaveNewBookResponse.class);
    }

    public static List<GetAllAuthorBooksResponse> requestSpecificationGetAllAuthorBooks(String authorId) {
        return given().spec(requestSpecification())
                .when()
                .get(Properties.GET_BOOKS_URI, authorId)
                .then()
                .spec(ResponseSpecifications.responseSpecificationGet())
                .extract().jsonPath().getList(".", GetAllAuthorBooksResponse.class);
    }

    public static GetAllAuthorBooksXmlResponse requestSpecificationGetAllAuthorBooksXml(int authorId) {
        GetAllAuthorBooksXmlRequest author = new GetAllAuthorBooksXmlRequest();
        author.setAuthorId(authorId);

        return given().spec(requestSpecificationXml())
                .body(author)
                .when()
                .post(Properties.POST_BOOKS_XML_URI)
                .then()
                .spec(ResponseSpecifications.responseSpecificationGet())
                .extract().as(GetAllAuthorBooksXmlResponse.class);
    }
}
