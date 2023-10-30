package steps.asserts;

import models.save_new_book.SaveNewBookResponse;

import static org.assertj.core.api.Assertions.assertThat;

public class AssertSaveNewBook {

    public static void assertionSavingNewBook(SaveNewBookResponse bookId) {
        assertThat(bookId).isNotNull();
    }
}
