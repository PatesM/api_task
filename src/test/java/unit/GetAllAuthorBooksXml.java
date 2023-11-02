package unit;

import static utils.StringGenerator.generateString;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import java.util.Set;
import models.add_new_author.SaveNewAuthorResponse;
import models.get_all_author_books_xml.GetAllAuthorBooksXmlResponse;
import models.negative_response.NegativeResponseForAllModels;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import steps.asserts.AssertGetAllAuthorBooksXml;
import steps.asserts.AssertNegativeResult;
import steps.specifications.RequestSpecifications;

@Epic("Post method testing")
@Story("Getting all the author's books in XML")
public class GetAllAuthorBooksXml {

    private static String bookTitle;
    private static Long authorId;
    private static String firstName;
    private static String familyName;

    @BeforeEach
    @Tag("SaveNewAuthorPrecondition")
    void setup(final TestInfo info) {
        final Set<String> testTags = info.getTags();
        if (testTags.stream().anyMatch(tag -> tag.equals("SkipBeforeEach"))) {
            return;
        }

        firstName = generateString(8);
        familyName = generateString(8);

        SaveNewAuthorResponse author = RequestSpecifications.requestSpecificationSaveNewAuthor(
            firstName, familyName, 201, "authorId", 3);
        authorId = author.getAuthorId();

        if (testTags.stream().anyMatch(tag -> tag.equals("NotEmptyAuthorBooksList"))) {
            bookTitle = generateString(20);
            RequestSpecifications.requestSpecificationSaveNewBookPositiveResult(bookTitle,
                authorId, 201, "bookId", 1);
        }
    }

    @Test
    @Tag("PositiveTest")
    @Tag("NotEmptyAuthorBooksList")
    @DisplayName("Getting all the author's books in XML")
    @Description("Should return list books of the author in XML with status code 200")
    public void gettingAllAuthorBooksXml() {
        GetAllAuthorBooksXmlResponse authorsBooks = RequestSpecifications.requestSpecificationGetAllAuthorBooksXmlPositiveResult(
            Math.toIntExact(authorId), 200);
        AssertGetAllAuthorBooksXml.assertionGettingAllAuthorBooksXmlPositiveResult(authorsBooks,
            bookTitle,
            authorId, firstName, familyName);
    }

    @Test
    @Tag("PositiveTest")
    @DisplayName("Getting all the author's books in XML from empty list")
    @Description("Should return empty list books of the author with status code 200")
    public void gettingAllAuthorBooksXmlEmptyList() {
        GetAllAuthorBooksXmlResponse authorsBooks = RequestSpecifications.requestSpecificationGetAllAuthorBooksXmlPositiveResult(
            Math.toIntExact(authorId), 200);
        AssertGetAllAuthorBooksXml.assertionGettingAllAuthorBooksXmlEmptyList(authorsBooks);
    }

    @Test
    @Tag("NegativeTest")
    @Tag("SkipBeforeEach")
    @DisplayName("Getting all the author's books in XML by a non-existent author")
    @Description("Should return error message and a status code 400")
    public void gettingAllAuthorBooksXmlNonExistentAuthor() {
        NegativeResponseForAllModels response = RequestSpecifications.requestSpecificationGetAllAuthorBooksXmlNegativeResult(
            "99999", 400);
        AssertNegativeResult.assertionNegativeResult(response, 1004,
            "Валидация не пройдена",
            "Указанный автор не существует в таблице");
    }

    @Test
    @Tag("NegativeTest")
    @Tag("SkipBeforeEach")
    @DisplayName("Getting all the author's books in XML with empty authorId")
    @Description("Should return error message and a status code 400")
    public void gettingAllAuthorBooksXmlWithNullInAuthorId() {
        NegativeResponseForAllModels response = RequestSpecifications.requestSpecificationGetAllAuthorBooksXmlNegativeResult(
            " ", 400);
        AssertNegativeResult.assertionNegativeResult(response, 1001,
            "Валидация не пройдена",
            "Не передан обязательный параметр: authorId");
    }
}
