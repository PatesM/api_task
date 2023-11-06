package steps.asserts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import models.save_new_book.SaveNewBookResponse;

public class AssertSaveNewBook {

    public void assertionSavingNewBookPositiveResult(SaveNewBookResponse book) {
        assertNotNull(book.getBookId());
    }

    public void assertionSavingSecondNewBookPositiveResult(SaveNewBookResponse book,
        Integer expectedId) {
        assertEquals(expectedId.longValue(), book.getBookId());
    }

}
