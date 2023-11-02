package unit;

import static utils.StringGenerator.generateString;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import java.util.Set;
import models.add_new_author.SaveNewAuthorResponse;
import models.save_new_book.SaveNewBookResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import steps.asserts.AssertSaveNewBook;
import steps.specifications.RequestSpecifications;

@Epic("Post method testing")
@Story("Saving a new book")
public class SaveNewBook {

    private static Long authorId;
    private static String bookTitleDuplicate;

    @BeforeEach
    @Tag("SaveNewAuthorPrecondition")
    void setup(final TestInfo info) {
        final Set<String> testTags = info.getTags();
        if (testTags.stream().anyMatch(tag -> tag.equals("SkipBeforeEach"))) {
            return;
        }

        String firstName = generateString(8);
        String familyName = generateString(8);

        SaveNewAuthorResponse author = RequestSpecifications.requestSpecificationSaveNewAuthor(
            firstName, familyName, 201);
        authorId = author.getAuthorId();

        if (testTags.stream().anyMatch(tag -> tag.equals("Duplicate"))) {
            bookTitleDuplicate = generateString(20);
            RequestSpecifications.requestSpecificationSaveNewBook(bookTitleDuplicate, authorId,
                201);
        }
    }

    @Test
    @Tag("PositiveTest")
    @DisplayName("Saving a new book with filled fields")
    @Description("Should save the new author's book and return the book id with a status code 201")
    public void savingNewBookWithFilledFields() {
        String bookTitle = generateString(20);

        SaveNewBookResponse book = RequestSpecifications.requestSpecificationSaveNewBook(bookTitle,
            authorId, 201);
        AssertSaveNewBook.assertionSavingNewBook(book);
    }

    @Test
    @Tag("PositiveTest")
    @DisplayName("Saving a new book with a 100-character bookTitle")
    @Description("Should save the new author's book and return the book id with a status code 201")
    public void savingNewBookWith100CharacterBookTitle() {
        String bookTitle = generateString(100);

        SaveNewBookResponse book = RequestSpecifications.requestSpecificationSaveNewBook(bookTitle,
            authorId, 201);
        AssertSaveNewBook.assertionSavingNewBook(book);
    }

    @Test
    @Tag("NegativeTest")
    @Tag("Duplicate")
    @DisplayName("Saving a new book duplicate")
    @Description("Should save the new author's book and return the book id with a status code 201")
    public void savingNewBookDuplicate() {
        SaveNewBookResponse book = RequestSpecifications.requestSpecificationSaveNewBook(
            bookTitleDuplicate, authorId, 201);
        AssertSaveNewBook.assertionSavingNewBook(book);
    }

    @Test
    @Tag("NegativeTest")
    @Tag("SkipBeforeEach")
    @DisplayName("Saving a new book with null in parameters")
    @Description("Should return error message and a status code 400")
    public void savingNewBookWithNullInParameters() {
        SaveNewBookResponse book = RequestSpecifications.requestSpecificationSaveNewBook(null, null,
            400);
        AssertSaveNewBook.assertionSavingNewBook(book);
    }

    @Test
    @Tag("NegativeTest")
    @DisplayName("Saving a new book with null in bookTitle")
    @Description("Should return error message and a status code 400")
    public void savingNewBookWithNullInBookTitle() {
        SaveNewBookResponse book = RequestSpecifications.requestSpecificationSaveNewBook(null,
            authorId, 400);
        AssertSaveNewBook.assertionSavingNewBook(book);
    }

    @Test
    @Tag("NegativeTest")
    @DisplayName("Saving a new book with empty bookTitle")
    @Description("Should return error message and a status code 400")
    public void savingNewBookWithEmptyBookTitle() {
        SaveNewBookResponse book = RequestSpecifications.requestSpecificationSaveNewBook("",
            authorId, 400);
        AssertSaveNewBook.assertionSavingNewBook(book);
    }

    @Test
    @Tag("NegativeTest")
    @DisplayName("Saving a new book with a 101-character bookTitle")
    @Description("Should return error message and a status code 400")
    public void savingNewBookWith101CharacterBookTitle() {
        String bookTitle = generateString(101);

        SaveNewBookResponse book = RequestSpecifications.requestSpecificationSaveNewBook(bookTitle,
            authorId, 400);
        AssertSaveNewBook.assertionSavingNewBook(book);
    }

    @Test
    @Tag("NegativeTest")
    @Tag("SkipBeforeEach")
    @DisplayName("Saving a new book by a non-existent author")
    @Description("Should save the new author's book and return the book id with a status code 201")
    public void savingNewBookWithNonExistentAuthor() {
        String bookTitle = generateString(20);

        SaveNewBookResponse book = RequestSpecifications.requestSpecificationSaveNewBook(bookTitle,
            99999L, 409);
        AssertSaveNewBook.assertionSavingNewBook(book);
    }
}
