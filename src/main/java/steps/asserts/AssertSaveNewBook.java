package steps.asserts;

import static org.junit.jupiter.api.Assertions.assertEquals;

import models.save_new_book.SaveNewBookResponse;

public class AssertSaveNewBook {

    public static void assertionSavingNewBookPositiveResult(SaveNewBookResponse book,
        Integer expectedId) {
        assertEquals(expectedId.longValue(), book.getBookId());
    }
}
