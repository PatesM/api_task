package unit;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import models.add_new_author.SaveNewAuthorResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import steps.asserts.AssertSaveNewAuthor;
import steps.specifications.RequestSpecifications;
import utils.StringGenerator;

@Epic("Post method testing")
@Story("Saving a new author")
public class SaveNewAuthor {

    @Test
    @DisplayName("Saving a new author")
    @Description("Should save the new author and return the author id with a status code 201")
    public void savingNewAuthor() {
        String firstName = StringGenerator.generateString();
        String familyName = StringGenerator.generateString();

        SaveNewAuthorResponse authorId = RequestSpecifications.requestSpecificationSaveNewAuthor(firstName, familyName);
        AssertSaveNewAuthor.assertionSavingNewAuthor(authorId);
    }
}
