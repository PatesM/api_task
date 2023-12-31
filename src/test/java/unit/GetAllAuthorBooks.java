package unit;

import static steps.request_steps.ApiMethods.authorizationApiMethod;
import static steps.request_steps.ApiMethods.saveNewAuthorApiMethod;
import static steps.request_steps.ApiMethods.saveNewBookApiMethod;
import static utils.DateGenerator.generateDate;
import static utils.StringGenerator.generateString;

import entity.AuthorTable;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import steps.asserts.AssertGetAllAuthorBooks;
import steps.asserts.AssertNegativeResult;
import steps.specifications.RequestSpecifications;

@Epic("Get method testing")
@Story("Getting all the author's books")
public class GetAllAuthorBooks {

    private final AssertGetAllAuthorBooks getAllAuthorBooks = new AssertGetAllAuthorBooks();
    private final AssertNegativeResult assertNegativeResult = new AssertNegativeResult();
    private static AuthorizationResponse authorizationResponse;
    private static SaveNewAuthorRequest authorRequest;
    private static Long authorId;
    private static String bookTitle;
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
    @DisplayName("Getting all the author's books")
    @Description("Should return list books of the author with status code 200")
    public void gettingAllAuthorBooks() {
        List<GetAllAuthorBooksResponse> books = RequestSpecifications
            .requestSpecificationGetAllAuthorBooksPositiveResult(authorizationResponse,
                String.valueOf(authorId), 200);

        getAllAuthorBooks.assertionGettingAllAuthorBooksPositiveResult(books, bookTitle, timeStamp,
            authorId, authorRequest);
    }

    @Test
    @Tag("PositiveTest")
    @DisplayName("Getting all the author's books from empty list")
    @Description("Should return empty list books of the author with status code 200")
    public void gettingAllAuthorBooksEmptyList() {
        List<GetAllAuthorBooksResponse> books = RequestSpecifications
            .requestSpecificationGetAllAuthorBooksPositiveResult(authorizationResponse,
                String.valueOf(authorId), 200);

        getAllAuthorBooks.assertionGettingAllAuthorBooksEmptyList(books);
    }

    static Stream<Arguments> argsProviderFactory() {
        return Stream.of(
            Arguments.of(
                "99999",
                400,
                1004,
                "��������� �� ��������",
                "��������� ����� �� ���������� � �������",
                "Getting all the author's books by a non-existent author"
            ),
            Arguments.of(
                " ",
                400,
                1001,
                "��������� �� ��������",
                "������������ ������������ ��������",
                "Getting all the author's books with empty authorId parameter"
            )
        );
    }

    @ParameterizedTest(name = "{5}")
    @MethodSource("argsProviderFactory")
    @Tag("NegativeTest")
    @DisplayName("Getting all the author's books with different request and response parameters")
    @Description("Should return error message and a status code 400")
    public void gettingAllAuthorBooksParameterizedTest(String authorId, int expectedStatusCode,
        Integer expectedErrorCode, String expectedErrorMessage, String expectedErrorDetails,
        String testName) {
        NegativeResponseForAllModels response = RequestSpecifications
            .requestSpecificationGetAllAuthorBooksNegativeResult(authorizationResponse, authorId,
                expectedStatusCode);

        assertNegativeResult.assertionNegativeResult(response,
            expectedErrorCode,
            expectedErrorMessage,
            expectedErrorDetails);
    }
}
