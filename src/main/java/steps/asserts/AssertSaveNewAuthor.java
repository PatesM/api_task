package steps.asserts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import models.add_new_author.SaveNewAuthorResponse;

public class AssertSaveNewAuthor {

    public static void assertionSavingNewAuthor(SaveNewAuthorResponse author, Long expectedId) {
        assertNotNull(author.getAuthorId());
        assertEquals(expectedId, author.getAuthorId());
    }
}
