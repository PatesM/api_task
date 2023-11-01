package steps.asserts;

import models.save_new_book.SaveNewBookResponse;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AssertSaveNewBook {

    public static void assertionSavingNewBook(SaveNewBookResponse book) {
        assertNotNull(book);
    }
}
