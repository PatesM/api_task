package unit;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import models.get_all_author_books_xml.GetAllAuthorBooksXmlResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import steps.asserts.AssertGetAllAuthorBooksXml;
import steps.specifications.RequestSpecifications;

@Epic("Post method testing")
@Story("Getting all the author's books in XML")
public class GetAllAuthorBooksXml {

    @Test
    @DisplayName("Getting all the author's books in XML")
    @Description("Should return list books of the author in XML with status code 200")
    public void gettingAllAuthorBooksXml() {
        GetAllAuthorBooksXmlResponse authorsBooks = RequestSpecifications.requestSpecificationGetAllAuthorBooksXml(16);
        AssertGetAllAuthorBooksXml.assertionGettingAllAuthorBooksXml(authorsBooks);
    }
}
