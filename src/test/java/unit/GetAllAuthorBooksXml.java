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
import models.get_all_author_books_xml.GetAllAuthorBooksXmlResponse;
import models.negative_response.NegativeResponseForAllModels;
import models.save_new_book.SaveNewBookRequest;
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

    private static SaveNewAuthorRequest authorRequest;
    private static String bookTitle;
    private static Long authorId;

    @BeforeEach
    @Tag("SaveNewAuthorPrecondition")
    void setup(final TestInfo info) {
        final Set<String> testTags = info.getTags();
        if (testTags.stream().anyMatch(tag -> tag.equals("SkipBeforeEach"))) {
            return;
        }

        authorRequest = new SaveNewAuthorRequest(generateString(8),
            generateString(8));

        SaveNewAuthorResponse authorResponse = saveNewAuthorApiMethod(authorRequest);
        authorId = authorResponse.getAuthorId();

        if (testTags.stream().anyMatch(tag -> tag.equals("NotEmptyAuthorBooksList"))) {
            SaveNewBookRequest bookRequest = new SaveNewBookRequest(generateString(20),
                new AuthorTable(authorId));
            bookTitle = bookRequest.getBookTitle();

            saveNewBookApiMethod(bookRequest);
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
            bookTitle, authorId, authorRequest);
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
