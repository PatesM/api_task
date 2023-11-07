package unit;

import static steps.request_steps.ApiMethods.authorizationApiMethod;
import static steps.request_steps.ApiMethods.saveNewAuthorApiMethod;
import static utils.StringGenerator.generateString;

import entity.BookTable;
import io.qameta.allure.Description;
import java.util.List;
import models.add_new_author.SaveNewAuthorRequest;
import models.add_new_author.SaveNewAuthorResponse;
import models.authorize_user.AuthorizationResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import steps.asserts.AssertSql;
import steps.data_base_steps.SqlMethods;

public class HibernateTest {

    private static AuthorizationResponse authorizationResponse;
    private static Long authorId = 1L;
    private final SqlMethods sqlMethods = new SqlMethods();
    private final AssertSql assertSql = new AssertSql();

    @BeforeAll
    @Tag("AuthorizationPrecondition")
    static void setAuthorization() {
        authorizationResponse = authorizationApiMethod();
    }

    @BeforeEach
    void setup() {
        SaveNewAuthorRequest authorRequest = new SaveNewAuthorRequest(generateString(8),
            generateString(8));

        SaveNewAuthorResponse authorResponse = saveNewAuthorApiMethod(authorizationResponse,
            authorRequest);
        authorId = authorResponse.getAuthorId();
    }

    @Test
    @Tag("PositiveTest")
    @DisplayName("Test for task 5.2.4")
    @Description("Should return second book")
    public void savingAndGettingAuthorBooks() {
        String firstBook = "War and Peace";
        String secondBook = "Anna Karenina";

        sqlMethods.deleteAll();

        sqlMethods.insertBook(firstBook, authorId);
        sqlMethods.insertBook(secondBook, authorId);

        List<BookTable> books = sqlMethods.findAll();
        assertSql.assertListSize(books, 2);
        System.out.println("Two books created: " + books);

        List<BookTable> firstBookList = sqlMethods.findBook(firstBook);
        assertSql.assertListSize(firstBookList, 1);
        System.out.println("First book: " + firstBookList);

        sqlMethods.deleteBook(firstBook);

        List<BookTable> secondBookList = sqlMethods.findBook(secondBook);
        assertSql.assertListSize(secondBookList, 1);
        System.out.println("Second book: " + secondBookList);
    }
}
