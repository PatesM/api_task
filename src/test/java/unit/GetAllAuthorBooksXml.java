package unit;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import lombok.extern.java.Log;
import models.add_new_author.SaveNewAuthorResponse;
import models.get_all_author_books_xml.GetAllAuthorBooksXmlResponse;
import org.junit.jupiter.api.*;
import steps.asserts.AssertGetAllAuthorBooksXml;
import steps.specifications.RequestSpecifications;

import java.util.Set;

import static utils.StringGenerator.generateString;

@Log
@Epic("Post method testing")
@Story("Getting all the author's books in XML")
public class GetAllAuthorBooksXml {

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
    @DisplayName("Getting all the author's books in XML")
    @Description("Should return list books of the author in XML with status code 200")
    public void gettingAllAuthorBooksXml() {
        log.info("Getting all the author's books in XML");

        GetAllAuthorBooksXmlResponse authorsBooks = RequestSpecifications.requestSpecificationGetAllAuthorBooksXml(Math.toIntExact(authorId), 200);
        AssertGetAllAuthorBooksXml.assertionGettingAllAuthorBooksXml(authorsBooks);

        log.info("Test passed successfully!");
    }

    @Test
    @Tag("PositiveTest")
    @DisplayName("Getting all the author's books in XML from empty list")
    @Description("Should return empty list books of the author with status code 200")
    public void gettingAllAuthorBooksXmlEmptyList() {
        log.info("Getting all the author's books in XML from empty list");

        GetAllAuthorBooksXmlResponse authorsBooks = RequestSpecifications.requestSpecificationGetAllAuthorBooksXml(Math.toIntExact(authorId), 200);
        AssertGetAllAuthorBooksXml.assertionGettingAllAuthorBooksXmlEmptyList(authorsBooks);

        log.info("Test passed successfully!");
    }

    @Test
    @Tag("NegativeTest")
    @Tag("SkipBeforeEach")
    @DisplayName("Getting all the author's books in XML by a non-existent author")
    @Description("Should return error message and a status code 400")
    public void gettingAllAuthorBooksXmlNonExistentAuthor() {
        log.info("Getting all the author's books in XML by a non-existent author");

        GetAllAuthorBooksXmlResponse authorsBooks = RequestSpecifications.requestSpecificationGetAllAuthorBooksXml(99999, 400);
        AssertGetAllAuthorBooksXml.assertionGettingAllAuthorBooksXml(authorsBooks);

        log.info("Test passed successfully!");
    }

    @Test
    @Tag("NegativeTest")
    @Tag("SkipBeforeEach")
    @DisplayName("Getting all the author's books in XML with null in authorId")
    @Description("Should return error message and a status code 400")
    public void gettingAllAuthorBooksXmlWithNullInAuthorId() {
        log.info("Getting all the author's books in XML with null in authorId");

        GetAllAuthorBooksXmlResponse authorsBooks = RequestSpecifications.requestSpecificationGetAllAuthorBooksXml(null, 400);
        AssertGetAllAuthorBooksXml.assertionGettingAllAuthorBooksXml(authorsBooks);

        log.info("Test passed successfully!");
    }
}
