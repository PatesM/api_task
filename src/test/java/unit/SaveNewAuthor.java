package unit;

import static steps.request_steps.ApiMethods.AuthorizationApiMethod;
import static utils.DateGenerator.generateDate;
import static utils.StringGenerator.generateString;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import models.add_new_author.SaveNewAuthorRequest;
import models.add_new_author.SaveNewAuthorResponse;
import models.authorize_user.AuthorizationResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import steps.asserts.AssertSaveNewAuthor;
import steps.specifications.RequestSpecifications;

@Epic("Post method testing")
@Story("Saving a new author")
public class SaveNewAuthor {

    private static AuthorizationResponse authorizationResponse;

    @BeforeAll
    @Tag("AuthorizationPrecondition")
    static void setAuthorization() {
        authorizationResponse = AuthorizationApiMethod();
    }

    @Test
    @DisplayName("Saving a new author")
    @Description("Should save the new author and return the author id with a status code 201")
    public void savingNewAuthor() {
        SaveNewAuthorRequest authorRequest = new SaveNewAuthorRequest(generateString(8),
            generateString(8), generateString(8), generateDate());

        SaveNewAuthorResponse authorResponse = RequestSpecifications.requestSpecificationSaveNewAuthor(
            authorizationResponse, authorRequest, 201, "authorId", 1);
        AssertSaveNewAuthor.assertionSavingNewAuthor(authorResponse, 1L);
    }
}
