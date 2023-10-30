package unit;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import models.save_new_book.SaveNewBookResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import steps.asserts.AssertSaveNewBook;
import steps.specifications.RequestSpecifications;
import utils.StringGenerator;

@Epic("Post method testing")
@Story("Saving a new book")
public class SaveNewBook {

    @Test
    @DisplayName("Saving a new book")
    @Description("Should save the new author's book and return the book id with a status code 200")
    public void savingNewBook() {
        String bookTitle = StringGenerator.generateString();

        SaveNewBookResponse bookId = RequestSpecifications.requestSpecificationSaveNewBook(bookTitle, 16);
        AssertSaveNewBook.assertionSavingNewBook(bookId);
    }
}
