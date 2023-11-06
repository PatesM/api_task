package unit;

import static steps.request_steps.ApiMethods.authorizationApiMethod;
import static steps.request_steps.ApiMethods.saveNewAuthorApiMethod;
import static steps.request_steps.ApiMethods.saveNewBookApiMethod;
import static steps.specifications.RequestSpecifications.requestSpecificationGetAllAuthorBooksXmlNegativeResult;
import static steps.specifications.RequestSpecifications.requestSpecificationGetAllAuthorBooksXmlPositiveResult;
import static utils.DateGenerator.generateDate;
import static utils.StringGenerator.generateString;

import entity.AuthorTable;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import java.util.Set;
import models.add_new_author.SaveNewAuthorRequest;
import models.add_new_author.SaveNewAuthorResponse;
import models.authorize_user.AuthorizationResponse;
import models.get_all_author_books_xml.GetAllAuthorBooksXmlResponse;
import models.negative_response.NegativeResponseForAllModels;
import models.save_new_book.SaveNewBookRequest;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import steps.asserts.AssertGetAllAuthorBooksXml;
import steps.asserts.AssertNegativeResult;

@Epic("Post method testing")
@Story("Getting all the author's books in XML")
public class GetAllAuthorBooksXml {

    private final AssertGetAllAuthorBooksXml assertGetAllAuthorBooksXml =
        new AssertGetAllAuthorBooksXml();
    private final AssertNegativeResult assertNegativeResult = new AssertNegativeResult();
    private static AuthorizationResponse authorizationResponse;
    private static SaveNewAuthorRequest authorRequest;
    private static String bookTitle;
    private static Long authorId;
    private static DateTime timeStamp;

    @BeforeAll
    @Tag("AuthorizationPrecondition")
    static void setAuthorization() {
        authorizationResponse = authorizationApiMethod();
    }

    @BeforeEach
    @Tag("SaveNewAuthorPrecondition")
    void setup(final TestInfo info) {
        final Set<String> testTags = info.getTags();
        if (testTags.stream().anyMatch(tag -> tag.equals("SkipBeforeEach"))) {
            return;
        }

        authorRequest = new SaveNewAuthorRequest(generateString(8), generateString(8),
            generateString(8), generateDate());

        SaveNewAuthorResponse authorResponse = saveNewAuthorApiMethod(authorizationResponse,
            authorRequest);
        authorId = authorResponse.getAuthorId();

        if (testTags.stream().anyMatch(tag -> tag.equals("NotEmptyAuthorBooksList"))) {
            SaveNewBookRequest bookRequest = new SaveNewBookRequest(generateString(20),
                new AuthorTable(authorId));
            bookTitle = bookRequest.getBookTitle();
            timeStamp = new DateTime().toDateTime(DateTimeZone.UTC);

            saveNewBookApiMethod(authorizationResponse, bookRequest);
        }
    }

    @Test
    @Tag("PositiveTest")
    @Tag("NotEmptyAuthorBooksList")
    @DisplayName("Getting all the author's books in XML")
    @Description("Should return list books of the author in XML with status code 200")
    public void gettingAllAuthorBooksXml() {
        GetAllAuthorBooksXmlResponse authorsBooks =
            requestSpecificationGetAllAuthorBooksXmlPositiveResult(Math.toIntExact(authorId),
                authorizationResponse, 200);

        assertGetAllAuthorBooksXml.assertionGettingAllAuthorBooksXmlPositiveResult(authorsBooks,
            bookTitle, timeStamp, authorId, authorRequest);
    }

    @Test
    @Tag("PositiveTest")
    @DisplayName("Getting all the author's books in XML from empty list")
    @Description("Should return empty list books of the author with status code 200")
    public void gettingAllAuthorBooksXmlEmptyList() {
        GetAllAuthorBooksXmlResponse authorsBooks =
            requestSpecificationGetAllAuthorBooksXmlPositiveResult(Math.toIntExact(authorId),
                authorizationResponse, 200);

        assertGetAllAuthorBooksXml.assertionGettingAllAuthorBooksXmlEmptyList(authorsBooks);
    }

    @Test
    @Tag("NegativeTest")
    @Tag("SkipBeforeEach")
    @DisplayName("Getting all the author's books in XML by a non-existent author")
    @Description("Should return error message and a status code 400")
    public void gettingAllAuthorBooksXmlNonExistentAuthor() {
        NegativeResponseForAllModels response =
            requestSpecificationGetAllAuthorBooksXmlNegativeResult(authorizationResponse, "99999",
                400);

        assertNegativeResult.assertionNegativeResult(response, 1004, "Валидация не пройдена",
            "Указанный автор не существует в таблице");
    }

    @Test
    @Tag("NegativeTest")
    @Tag("SkipBeforeEach")
    @DisplayName("Getting all the author's books in XML with empty authorId")
    @Description("Should return error message and a status code 400")
    public void gettingAllAuthorBooksXmlWithNullInAuthorId() {
        NegativeResponseForAllModels response =
            requestSpecificationGetAllAuthorBooksXmlNegativeResult(authorizationResponse, " ", 400);
        assertNegativeResult.assertionNegativeResult(response, 1001, "Валидация не пройдена",
            "Не передан обязательный параметр: authorId");
    }
}
