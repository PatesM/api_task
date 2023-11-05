package unit;

import static steps.request_steps.ApiMethods.saveNewAuthorApiMethod;
import static steps.request_steps.ApiMethods.saveNewBookApiMethod;
import static utils.StringGenerator.generateString;

import entity.AuthorTable;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import java.util.Set;
import models.add_new_author.SaveNewAuthorRequest;
import models.add_new_author.SaveNewAuthorResponse;
import models.negative_response.NegativeResponseForAllModels;
import models.save_new_book.SaveNewBookRequest;
import models.save_new_book.SaveNewBookResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import steps.asserts.AssertNegativeResult;
import steps.asserts.AssertSaveNewBook;
import steps.specifications.RequestSpecifications;

@Epic("Post method testing")
@Story("Saving a new book")
public class SaveNewBook {

    private static Long authorId;
    private static SaveNewBookResponse bookResponse;

    @BeforeEach
    @Tag("SaveNewAuthorPrecondition")
    void setup(final TestInfo info) {
        final Set<String> testTags = info.getTags();
        if (testTags.stream().anyMatch(tag -> tag.equals("SkipBeforeEach"))) {
            return;
        }

        SaveNewAuthorRequest authorRequest = new SaveNewAuthorRequest(generateString(8),
            generateString(8));

        SaveNewAuthorResponse authorResponse = saveNewAuthorApiMethod(authorRequest);
        authorId = authorResponse.getAuthorId();

        if (testTags.stream().anyMatch(tag -> tag.equals("SecondBook"))) {
            SaveNewBookRequest bookRequest = new SaveNewBookRequest(generateString(20),
                new AuthorTable(authorId));

            bookResponse = saveNewBookApiMethod(bookRequest);
        }
    }

    @Test
    @Tag("PositiveTest")
    @DisplayName("Saving a new book with filled fields")
    @Description("Should save the new author's book and return the book id with a status code 201")
    public void savingNewBookWithFilledFields() {
        String bookTitle = generateString(20);

        SaveNewBookResponse book = RequestSpecifications.requestSpecificationSaveNewBookPositiveResult(
            bookTitle, authorId, 201, "bookId");
        AssertSaveNewBook.assertionSavingNewBookPositiveResult(book);
    }

    @Test
    @Tag("PositiveTest")
    @DisplayName("Saving a new book with a 100-character bookTitle")
    @Description("Should save the new author's book and return the book id with a status code 201")
    public void savingNewBookWith100CharacterBookTitle() {
        String bookTitle = generateString(100);

        SaveNewBookResponse book = RequestSpecifications.requestSpecificationSaveNewBookPositiveResult(
            bookTitle, authorId, 201, "bookId");
        AssertSaveNewBook.assertionSavingNewBookPositiveResult(book);
    }

    @Test
    @Tag("PositiveTest")
    @Tag("SecondBook")
    @DisplayName("Saving the author's second book")
    @Description("Should save the new author's book and return the book id with a status code 201")
    public void savingSecondNewBook() {
        String bookTitle = generateString(20);

        SaveNewBookResponse book = RequestSpecifications.requestSpecificationSaveNewBookPositiveResult(
            bookTitle, authorId, 201, "bookId");
        AssertSaveNewBook.assertionSavingSecondNewBookPositiveResult(book,
            (int) (bookResponse.getBookId() + 1));
    }

    @Test
    @Tag("NegativeTest")
    @Tag("SkipBeforeEach")
    @DisplayName("Saving a new book with null in parameters")
    @Description("Should return error message and a status code 400")
    public void savingNewBookWithNullInParameters() {
        NegativeResponseForAllModels response = RequestSpecifications.requestSpecificationSaveNewBookNegativeResult(
            null, null, 400, 1001, "Валидация не пройдена",
            "Не передан обязательный параметр: bookTitle");
        AssertNegativeResult.assertionNegativeResult(response, 1001,
            "Валидация не пройдена",
            "Не передан обязательный параметр: bookTitle");
    }

    @Test
    @Tag("NegativeTest")
    @DisplayName("Saving a new book with null in bookTitle")
    @Description("Should return error message and a status code 400")
    public void savingNewBookWithNullInBookTitle() {
        NegativeResponseForAllModels response = RequestSpecifications.requestSpecificationSaveNewBookNegativeResult(
            null, authorId, 400, 1001, "Валидация не пройдена",
            "Не передан обязательный параметр: bookTitle");
        AssertNegativeResult.assertionNegativeResult(response, 1001,
            "Валидация не пройдена",
            "Не передан обязательный параметр: bookTitle");
    }

    @Test
    @Tag("NegativeTest")
    @DisplayName("Saving a new book with empty bookTitle")
    @Description("Should return error message and a status code 400")
    public void savingNewBookWithEmptyBookTitle() {
        NegativeResponseForAllModels response = RequestSpecifications.requestSpecificationSaveNewBookNegativeResult(
            "", authorId, 400, 1001, "Валидация не пройдена",
            "Некорректный размер поля bookTitle");
        AssertNegativeResult.assertionNegativeResult(response, 1001,
            "Валидация не пройдена",
            "Некорректный размер поля bookTitle");
    }

    @Test
    @Tag("NegativeTest")
    @DisplayName("Saving a new book with a 101-character bookTitle")
    @Description("Should return error message and a status code 400")
    public void savingNewBookWith101CharacterBookTitle() {
        String bookTitle = generateString(101);

        NegativeResponseForAllModels response = RequestSpecifications.requestSpecificationSaveNewBookNegativeResult(
            bookTitle, authorId, 400, 1001, "Валидация не пройдена",
            "Некорректный размер поля bookTitle");
        AssertNegativeResult.assertionNegativeResult(response, 1001,
            "Валидация не пройдена",
            "Некорректный размер поля bookTitle");
    }

    @Test
    @Tag("NegativeTest")
    @Tag("SkipBeforeEach")
    @DisplayName("Saving a new book by a non-existent author")
    @Description("Should save the new author's book and return the book id with a status code 201")
    public void savingNewBookWithNonExistentAuthor() {
        String bookTitle = generateString(20);

        NegativeResponseForAllModels response = RequestSpecifications.requestSpecificationSaveNewBookNegativeResult(
            bookTitle, 99999L, 409, 1001, "Валидация не пройдена",
            "Указанный автор не существует в таблице");
        AssertNegativeResult.assertionNegativeResult(response, 1001,
            "Валидация не пройдена",
            "Указанный автор не существует в таблице");
    }
}
