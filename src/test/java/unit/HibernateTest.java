package unit;

import static steps.data_base_steps.SqlMethods.deleteAll;
import static steps.data_base_steps.SqlMethods.findAll;
import static steps.request_steps.ApiMethods.AuthorizationApiMethod;
import static steps.request_steps.ApiMethods.SaveNewBookApiMethod;
import static utils.StringGenerator.generateString;

import entity.AuthorTable;
import entity.BookTable;
import io.qameta.allure.Description;
import java.util.List;
import models.authorize_user.AuthorizationResponse;
import models.save_new_book.SaveNewBookRequest;
import models.save_new_book.SaveNewBookResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class HibernateTest {

    private static AuthorizationResponse authorizationResponse;
    private static Long authorId;
    private static List<SaveNewBookResponse> bookResponseList;

    @BeforeAll
    @Tag("AuthorizationPrecondition")
    static void setAuthorization() {
        authorizationResponse = AuthorizationApiMethod();
    }

    @BeforeEach
    @Tag("AuthorizationPrecondition")
    void clearBookTable() {
        deleteAll();
    }

    @Test
    @Tag("PositiveTest")
    @DisplayName("Test for task 5.2.4")
    @Description("Should save")
    public void savingAndGettingAuthorBooks() {
        SaveNewBookRequest bookRequestFirst = new SaveNewBookRequest(generateString(20),
            new AuthorTable(authorId));
        SaveNewBookRequest bookRequestSecond = new SaveNewBookRequest(generateString(20),
            new AuthorTable(authorId));

        bookResponseList.add(0, SaveNewBookApiMethod(authorizationResponse, bookRequestFirst));
        bookResponseList.add(0, SaveNewBookApiMethod(authorizationResponse, bookRequestSecond));

        List<BookTable> bookTableList = findAll();
        System.out.println(bookTableList);

    }
}
