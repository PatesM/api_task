package unit;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import models.get_all_author_books.GetAllAuthorBooksResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import steps.asserts.AssertGetAllAuthorBooks;
import steps.specifications.RequestSpecifications;

import java.util.List;

@Epic("Get method testing")
@Story("Getting all the author's books")
public class GetAllAuthorBooks {

    @Test
    @DisplayName("Getting all the author's books")
    @Description("Should return list books of the author with status code 200")
    public void gettingAllAuthorBooks() {
        List<GetAllAuthorBooksResponse> books = RequestSpecifications.requestSpecificationGetAllAuthorBooks("16");
        AssertGetAllAuthorBooks.assertionGettingAllAuthorBooks(books);
    }
}
