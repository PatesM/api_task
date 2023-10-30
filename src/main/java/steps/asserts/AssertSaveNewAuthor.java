package steps.asserts;

import models.add_new_author.SaveNewAuthorResponse;

import static org.assertj.core.api.Assertions.assertThat;

public class AssertSaveNewAuthor {

    public static void assertionSavingNewAuthor(SaveNewAuthorResponse authorId) {
        assertThat(authorId).isNotNull();
    }
}
