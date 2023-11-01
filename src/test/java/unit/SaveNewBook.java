package unit;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import lombok.extern.java.Log;
import models.add_new_author.SaveNewAuthorResponse;
import models.save_new_book.SaveNewBookResponse;
import org.junit.jupiter.api.*;
import steps.asserts.AssertSaveNewBook;
import steps.specifications.RequestSpecifications;

import java.util.Set;

import static utils.StringGenerator.generateString;

@Log
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
        log.info("BeforeEach starting");

        String firstName = generateString(8);
        String familyName = generateString(8);

        SaveNewAuthorResponse author = RequestSpecifications.requestSpecificationSaveNewAuthor(firstName, familyName, 201);
        authorId = author.getAuthorId();

        if (testTags.stream().anyMatch(tag -> tag.equals("Duplicate"))) {
            log.info("Duplicate starting");
            bookTitleDuplicate = generateString(20);
            RequestSpecifications.requestSpecificationSaveNewBook(bookTitleDuplicate, authorId, 201);
        }
        log.info("BeforeEach completed\n");
    }

    @Test
    @Tag("PositiveTest")
    @DisplayName("Saving a new book with filled fields")
    @Description("Should save the new author's book and return the book id with a status code 201")
    public void savingNewBookWithFilledFields() {
        log.info("Saving a new book with filled fields");

        String bookTitle = generateString(20);

        SaveNewBookResponse book = RequestSpecifications.requestSpecificationSaveNewBook(bookTitle, authorId, 201);
        AssertSaveNewBook.assertionSavingNewBook(book);

        log.info("Test passed successfully!");
    }

    @Test
    @Tag("PositiveTest")
    @DisplayName("Saving a new book with a 100-character bookTitle")
    @Description("Should save the new author's book and return the book id with a status code 201")
    public void savingNewBookWith100CharacterBookTitle() {
        log.info("Saving a new book with a 100-character bookTitle");

        String bookTitle = generateString(100);

        SaveNewBookResponse book = RequestSpecifications.requestSpecificationSaveNewBook(bookTitle, authorId, 201);
        AssertSaveNewBook.assertionSavingNewBook(book);

        log.info("Test passed successfully!");
    }

    @Test
    @Tag("NegativeTest")
    @Tag("Duplicate")
    @DisplayName("Saving a new book duplicate")
    @Description("Should save the new author's book and return the book id with a status code 201")
    public void savingNewBookDuplicate() {
        log.info("Saving a new book duplicate");

        SaveNewBookResponse book = RequestSpecifications.requestSpecificationSaveNewBook(bookTitleDuplicate, authorId, 201);
        AssertSaveNewBook.assertionSavingNewBook(book);

        log.info("Test passed successfully!");
    }

    @Test
    @Tag("NegativeTest")
    @Tag("SkipBeforeEach")
    @DisplayName("Saving a new book with null in parameters")
    @Description("Should return error message and a status code 400")
    public void savingNewBookWithNullInParameters() {
        log.info("Saving a new book with null in parameters");

        SaveNewBookResponse book = RequestSpecifications.requestSpecificationSaveNewBook(null, null, 400);
        AssertSaveNewBook.assertionSavingNewBook(book);

        log.info("Test passed successfully!");
    }

    @Test
    @Tag("NegativeTest")
    @DisplayName("Saving a new book with null in bookTitle")
    @Description("Should return error message and a status code 400")
    public void savingNewBookWithNullInBookTitle() {
        log.info("Saving a new book with null in bookTitle");

        SaveNewBookResponse book = RequestSpecifications.requestSpecificationSaveNewBook(null, authorId, 400);
        AssertSaveNewBook.assertionSavingNewBook(book);

        log.info("Test passed successfully!");
    }

    @Test
    @Tag("NegativeTest")
    @DisplayName("Saving a new book with empty bookTitle")
    @Description("Should return error message and a status code 400")
    public void savingNewBookWithEmptyBookTitle() {
        log.info("Saving a new book with empty bookTitle");

        SaveNewBookResponse book = RequestSpecifications.requestSpecificationSaveNewBook("", authorId, 400);
        AssertSaveNewBook.assertionSavingNewBook(book);

        log.info("Test passed successfully!");
    }

    @Test
    @Tag("NegativeTest")
    @DisplayName("Saving a new book with a 101-character bookTitle")
    @Description("Should return error message and a status code 400")
    public void savingNewBookWith101CharacterBookTitle() {
        log.info("Saving a new book with a 101-character bookTitle");

        String bookTitle = generateString(101);

        SaveNewBookResponse book = RequestSpecifications.requestSpecificationSaveNewBook(bookTitle, authorId, 400);
        AssertSaveNewBook.assertionSavingNewBook(book);

        log.info("Test passed successfully!");
    }

    @Test
    @Tag("NegativeTest")
    @Tag("SkipBeforeEach")
    @DisplayName("Saving a new book by a non-existent author")
    @Description("Should save the new author's book and return the book id with a status code 201")
    public void savingNewBookWithNonExistentAuthor() {
        log.info("Saving a new book by a non-existent author");

        String bookTitle = generateString(20);

        SaveNewBookResponse book = RequestSpecifications.requestSpecificationSaveNewBook(bookTitle, 99999L, 409);
        AssertSaveNewBook.assertionSavingNewBook(book);

        log.info("Test passed successfully!");
    }
}
