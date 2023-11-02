package steps.asserts;

import static org.assertj.core.api.Java6Assertions.assertThat;

import models.save_new_book.SaveNewBookResponse;

public class AssertSaveNewBook {

    public static void assertionSavingNewBookPositiveResult(SaveNewBookResponse book,
        Integer expectedId) {
        assertThat(book.getBookId()).isEqualTo(expectedId.longValue());
    }
}
