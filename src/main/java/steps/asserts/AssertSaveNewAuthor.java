package steps.asserts;

import models.add_new_author.SaveNewAuthorResponse;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AssertSaveNewAuthor {

    public static void assertionSavingNewAuthor(SaveNewAuthorResponse author) {
        assertNotNull(author);
    }
}
