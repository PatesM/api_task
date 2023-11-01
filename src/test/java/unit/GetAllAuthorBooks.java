package unit;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import lombok.extern.java.Log;
import models.add_new_author.SaveNewAuthorResponse;
import models.get_all_author_books.GetAllAuthorBooksResponse;
import org.junit.jupiter.api.*;
import steps.asserts.AssertGetAllAuthorBooks;
import steps.specifications.RequestSpecifications;

import java.util.List;
import java.util.Set;

import static utils.StringGenerator.generateString;

@Log
@Epic("Get method testing")
@Story("Getting all the author's books")
public class GetAllAuthorBooks {

    private static Long authorId;

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

        if (testTags.stream().anyMatch(tag -> tag.equals("NotEmptyAuthorBooksList"))) {
            log.info("Saving a new book starting");

            String bookTitle = generateString(20);
            RequestSpecifications.requestSpecificationSaveNewBook(bookTitle, authorId, 201);
        }

        log.info("BeforeEach completed\n");
    }

    @Test
    @Tag("PositiveTest")
    @Tag("NotEmptyAuthorBooksList")
    @DisplayName("Getting all the author's books")
    @Description("Should return list books of the author with status code 200")
    public void gettingAllAuthorBooks() {
        log.info("Getting all the author's books");

        List<GetAllAuthorBooksResponse> books = RequestSpecifications.requestSpecificationGetAllAuthorBooks(String.valueOf(authorId), 200);
        AssertGetAllAuthorBooks.assertionGettingAllAuthorBooks(books);

        log.info("Test passed successfully!");
    }


    @Test
    @Tag("PositiveTest")
    @DisplayName("Getting all the author's books from empty list")
    @Description("Should return empty list books of the author with status code 200")
    public void gettingAllAuthorBooksEmptyList() {
        log.info("Getting all the author's books from empty list");

        List<GetAllAuthorBooksResponse> books = RequestSpecifications.requestSpecificationGetAllAuthorBooks(String.valueOf(authorId), 200);
        AssertGetAllAuthorBooks.assertionGettingAllAuthorBooksEmptyList(books);

        log.info("Test passed successfully!");
    }

    @Test
    @Tag("NegativeTest")
    @Tag("SkipBeforeEach")
    @DisplayName("Getting all the author's books by a non-existent author")
    @Description("Should return error message and a status code 409")
    public void gettingAllAuthorBooksNonExistentAuthor() {
        log.info("Getting all the author's books by a non-existent author");

        List<GetAllAuthorBooksResponse> books = RequestSpecifications.requestSpecificationGetAllAuthorBooks("99999", 409);
        AssertGetAllAuthorBooks.assertionGettingAllAuthorBooksEmptyList(books);

        log.info("Test passed successfully!");
    }

    @Test
    @Tag("NegativeTest")
    @Tag("SkipBeforeEach")
    @DisplayName("Getting all the author's books with empty authorId parameter")
    @Description("Should return error message and a status code 400")
    public void gettingAllAuthorBooksWithEmptyAuthorId() {
        log.info("Getting all the author's books with empty authorId parameter");

        List<GetAllAuthorBooksResponse> books = RequestSpecifications.requestSpecificationGetAllAuthorBooks(" ", 400);
        AssertGetAllAuthorBooks.assertionGettingAllAuthorBooksEmptyList(books);

        log.info("Test passed successfully!");
    }

}
