package unit;

import static steps.request_steps.ApiMethods.authorizationApiMethod;
import static steps.request_steps.ApiMethods.saveNewAuthorApiMethod;
import static steps.request_steps.ApiMethods.saveNewBookApiMethod;
import static steps.specifications.RequestSpecifications.requestSpecificationSaveNewBookNegativeResult;
import static utils.StringGenerator.generateString;

import entity.AuthorTable;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import java.util.Set;
import java.util.stream.Stream;
import models.add_new_author.SaveNewAuthorRequest;
import models.add_new_author.SaveNewAuthorResponse;
import models.authorize_user.AuthorizationResponse;
import models.negative_response.NegativeResponseForAllModels;
import models.save_new_book.SaveNewBookRequest;
import models.save_new_book.SaveNewBookResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import steps.asserts.AssertNegativeResult;
import steps.asserts.AssertSaveNewBook;
import steps.specifications.RequestSpecifications;

@Epic("Post method testing")
@Story("Saving a new book")
public class SaveNewBook {

    private final AssertSaveNewBook assertSaveNewBook = new AssertSaveNewBook();
    private final AssertNegativeResult assertNegativeResult = new AssertNegativeResult();
    private static AuthorizationResponse authorizationResponse;
    private static Long authorId;
    private static SaveNewBookResponse bookResponse;

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

        SaveNewAuthorRequest authorRequest = new SaveNewAuthorRequest(generateString(8),
            generateString(8));

        SaveNewAuthorResponse authorResponse = saveNewAuthorApiMethod(authorizationResponse,
            authorRequest);
        authorId = authorResponse.getAuthorId();

        if (testTags.stream().anyMatch(tag -> tag.equals("SecondBook"))) {
            SaveNewBookRequest bookRequest = new SaveNewBookRequest(generateString(20),
                new AuthorTable(authorId));

            bookResponse = saveNewBookApiMethod(authorizationResponse, bookRequest);
        }
    }

    @Test
    @Tag("PositiveTest")
    @DisplayName("Saving a new book with filled fields")
    @Description("Should save the new author's book and return the book id with a status code 201")
    public void savingNewBookWithFilledFields() {
        String bookTitle = generateString(20);

        SaveNewBookResponse book = RequestSpecifications
            .requestSpecificationSaveNewBookPositiveResult(authorizationResponse, bookTitle,
                authorId, 201, "bookId");

        assertSaveNewBook.assertionSavingNewBookPositiveResult(book);
    }

    @Test
    @Tag("PositiveTest")
    @DisplayName("Saving a new book with a 100-character bookTitle")
    @Description("Should save the new author's book and return the book id with a status code 201")
    public void savingNewBookWith100CharacterBookTitle() {
        String bookTitle = generateString(100);

        SaveNewBookResponse book = RequestSpecifications
            .requestSpecificationSaveNewBookPositiveResult(authorizationResponse, bookTitle,
                authorId, 201, "bookId");

        assertSaveNewBook.assertionSavingNewBookPositiveResult(book);
    }

    @Test
    @Tag("PositiveTest")
    @Tag("SecondBook")
    @DisplayName("Saving the author's second book")
    @Description("Should save the new author's book and return the book id with a status code 201")
    public void savingNewBookSecondBook() {
        String bookTitle = generateString(20);

        SaveNewBookResponse book = RequestSpecifications
            .requestSpecificationSaveNewBookPositiveResult(authorizationResponse, bookTitle,
                authorId, 201, "bookId");

        assertSaveNewBook.assertionSavingSecondNewBookPositiveResult(book,
            (int) (bookResponse.getBookId() + 1));
    }

    static Stream<Arguments> argsProviderFactory() {
        return Stream.of(
            Arguments.of(
                null,
                null,
                400,
                1001,
                "Валидация не пройдена",
                "Не передан обязательный параметр: bookTitle",
                "Saving a new book with null in parameters"
            ),
            Arguments.of(
                null,
                authorId,
                400,
                1001,
                "Валидация не пройдена",
                "Не передан обязательный параметр: bookTitle",
                "Saving a new book with null in bookTitle"
            ),
            Arguments.of(
                "",
                authorId,
                400,
                1001,
                "Валидация не пройдена",
                "Некорректный размер поля bookTitle",
                "Saving a new book with empty bookTitle"
            ),
            Arguments.of(
                generateString(101),
                authorId,
                400,
                1001,
                "Валидация не пройдена",
                "Некорректный размер поля bookTitle",
                "Saving a new book with a 101-character bookTitle"
            ),
            Arguments.of(
                generateString(20),
                99999L,
                409,
                1001,
                "Валидация не пройдена",
                "Указанный автор не существует в таблице",
                "Saving a new book by a non-existent author"
            )
        );
    }

    @ParameterizedTest(name = "{6}")
    @MethodSource("argsProviderFactory")
    @Tag("NegativeTest")
    @DisplayName("Saving a new book with different request and response parameters")
    @Description("Should return error message and a status code 400")
    public void savingNewBookParameterizedTest(String bookTitle, Long authorId,
        int expectedStatusCode,
        Integer expectedErrorCode, String expectedErrorMessage, String expectedErrorDetails,
        String testName) {

        NegativeResponseForAllModels response = requestSpecificationSaveNewBookNegativeResult(
            authorizationResponse, bookTitle, authorId, expectedStatusCode);

        assertNegativeResult.assertionNegativeResult(response, expectedErrorCode,
            expectedErrorMessage, expectedErrorDetails);
    }
}
