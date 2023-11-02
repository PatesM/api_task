package unit;

import static utils.StringGenerator.generateString;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import models.add_new_author.SaveNewAuthorResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import steps.asserts.AssertSaveNewAuthor;
import steps.specifications.RequestSpecifications;

@Epic("Post method testing")
@Story("Saving a new author")
public class SaveNewAuthor {

    @Test
    @DisplayName("Saving a new author")
    @Description("Should save the new author and return the author id with a status code 201")
    public void savingNewAuthor() {
        String firstName = generateString(8);
        String familyName = generateString(8);

        SaveNewAuthorResponse author = RequestSpecifications.requestSpecificationSaveNewAuthor(
            firstName, familyName, 201, "authorId", 1);
        AssertSaveNewAuthor.assertionSavingNewAuthor(author, 1L);
    }
}
