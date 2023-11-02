package steps.asserts;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import models.add_new_author.SaveNewAuthorResponse;

public class AssertSaveNewAuthor {

    public static void assertionSavingNewAuthor(SaveNewAuthorResponse author, Long id) {
        assertNotNull(author.getAuthorId());
        assertThat(author.getAuthorId()).isEqualTo(id);
    }
}
