package unit;

import static utils.StringGenerator.generateString;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import java.util.List;
import java.util.Set;
import models.add_new_author.SaveNewAuthorResponse;
import models.get_all_author_books.GetAllAuthorBooksResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import steps.asserts.AssertGetAllAuthorBooks;
import steps.specifications.RequestSpecifications;

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

        String firstName = generateString(8);
        String familyName = generateString(8);

        SaveNewAuthorResponse author = RequestSpecifications.requestSpecificationSaveNewAuthor(
            firstName, familyName, 201);
        authorId = author.getAuthorId();

        if (testTags.stream().anyMatch(tag -> tag.equals("NotEmptyAuthorBooksList"))) {
            String bookTitle = generateString(20);
            RequestSpecifications.requestSpecificationSaveNewBook(bookTitle, authorId, 201);
        }
    }

    @Test
    @Tag("PositiveTest")
    @Tag("NotEmptyAuthorBooksList")
    @DisplayName("Getting all the author's books")
    @Description("Should return list books of the author with status code 200")
    public void gettingAllAuthorBooks() {
        List<GetAllAuthorBooksResponse> books = RequestSpecifications.requestSpecificationGetAllAuthorBooks(
            String.valueOf(authorId), 200);
        AssertGetAllAuthorBooks.assertionGettingAllAuthorBooks(books);
    }

    @Test
    @Tag("PositiveTest")
    @DisplayName("Getting all the author's books from empty list")
    @Description("Should return empty list books of the author with status code 200")
    public void gettingAllAuthorBooksEmptyList() {
        List<GetAllAuthorBooksResponse> books = RequestSpecifications.requestSpecificationGetAllAuthorBooks(
            String.valueOf(authorId), 200);
        AssertGetAllAuthorBooks.assertionGettingAllAuthorBooksEmptyList(books);
    }

    @Test
    @Tag("NegativeTest")
    @Tag("SkipBeforeEach")
    @DisplayName("Getting all the author's books by a non-existent author")
    @Description("Should return error message and a status code 409")
    public void gettingAllAuthorBooksNonExistentAuthor() {
        List<GetAllAuthorBooksResponse> books = RequestSpecifications.requestSpecificationGetAllAuthorBooks(
            "99999", 409);
        AssertGetAllAuthorBooks.assertionGettingAllAuthorBooksEmptyList(books);
    }

    @Test
    @Tag("NegativeTest")
    @Tag("SkipBeforeEach")
    @DisplayName("Getting all the author's books with empty authorId parameter")
    @Description("Should return error message and a status code 400")
    public void gettingAllAuthorBooksWithEmptyAuthorId() {
        List<GetAllAuthorBooksResponse> books = RequestSpecifications.requestSpecificationGetAllAuthorBooks(
            " ", 400);
        AssertGetAllAuthorBooks.assertionGettingAllAuthorBooksEmptyList(books);
    }
}
