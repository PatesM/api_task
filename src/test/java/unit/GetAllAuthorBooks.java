package unit;

import static steps.request_steps.ApiMethods.AuthorizationApiMethod;
import static steps.request_steps.ApiMethods.SaveNewAuthorApiMethod;
import static steps.request_steps.ApiMethods.SaveNewBookApiMethod;
import static utils.DateGenerator.generateDate;
import static utils.StringGenerator.generateString;

import entity.AuthorTable;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import java.util.List;
import java.util.Set;
import models.add_new_author.SaveNewAuthorRequest;
import models.add_new_author.SaveNewAuthorResponse;
import models.authorize_user.AuthorizationResponse;
import models.get_all_author_books.GetAllAuthorBooksResponse;
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
import steps.asserts.AssertGetAllAuthorBooks;
import steps.asserts.AssertNegativeResult;
import steps.specifications.RequestSpecifications;

@Epic("Get method testing")
@Story("Getting all the author's books")
public class GetAllAuthorBooks {

    private static AuthorizationResponse authorizationResponse;
    private static SaveNewAuthorRequest authorRequest;
    private static Long authorId;
    private static String bookTitle;
    private static DateTime timeStamp;

    @BeforeAll
    @Tag("AuthorizationPrecondition")
    static void setAuthorization() {
        authorizationResponse = AuthorizationApiMethod();
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

        SaveNewAuthorResponse authorResponse = SaveNewAuthorApiMethod(authorizationResponse,
            authorRequest);
        authorId = authorResponse.getAuthorId();

        if (testTags.stream().anyMatch(tag -> tag.equals("NotEmptyAuthorBooksList"))) {
            SaveNewBookRequest bookRequest = new SaveNewBookRequest(generateString(20),
                new AuthorTable(authorId));
            bookTitle = bookRequest.getBookTitle();
            timeStamp = new DateTime().toDateTime(DateTimeZone.UTC);

            SaveNewBookApiMethod(authorizationResponse, bookRequest);
        }
    }

    @Test
    @Tag("PositiveTest")
    @Tag("NotEmptyAuthorBooksList")
    @DisplayName("Getting all the author's books")
    @Description("Should return list books of the author with status code 200")
    public void gettingAllAuthorBooks() {
        List<GetAllAuthorBooksResponse> books = RequestSpecifications.requestSpecificationGetAllAuthorBooksPositiveResult(
            authorizationResponse, String.valueOf(authorId), 200);
        AssertGetAllAuthorBooks.assertionGettingAllAuthorBooksPositiveResult(books, bookTitle,
            timeStamp, authorId, authorRequest);
    }

    @Test
    @Tag("PositiveTest")
    @DisplayName("Getting all the author's books from empty list")
    @Description("Should return empty list books of the author with status code 200")
    public void gettingAllAuthorBooksEmptyList() {
        List<GetAllAuthorBooksResponse> books = RequestSpecifications.requestSpecificationGetAllAuthorBooksPositiveResult(
            authorizationResponse, String.valueOf(authorId), 200);
        AssertGetAllAuthorBooks.assertionGettingAllAuthorBooksEmptyList(books);
    }

    @Test
    @Tag("NegativeTest")
    @Tag("SkipBeforeEach")
    @DisplayName("Getting all the author's books by a non-existent author")
    @Description("Should return error message and a status code 409")
    public void gettingAllAuthorBooksNonExistentAuthor() {
        NegativeResponseForAllModels response = RequestSpecifications.requestSpecificationGetAllAuthorBooksNegativeResult(
            authorizationResponse, "99999", 400);
        AssertNegativeResult.assertionNegativeResult(response, 1004,
            "Валидация не пройдена",
            "Указанный автор не существует в таблице");
    }

    @Test
    @Tag("NegativeTest")
    @Tag("SkipBeforeEach")
    @DisplayName("Getting all the author's books with empty authorId parameter")
    @Description("Should return error message and a status code 400")
    public void gettingAllAuthorBooksWithEmptyAuthorId() {
        NegativeResponseForAllModels response = RequestSpecifications.requestSpecificationGetAllAuthorBooksNegativeResult(
            authorizationResponse, " ", 400);
        AssertNegativeResult.assertionNegativeResult(response, 1001,
            "Валидация не пройдена",
            "Некорректный обязательный параметр");
    }
}
