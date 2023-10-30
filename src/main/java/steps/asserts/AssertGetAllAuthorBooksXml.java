package steps.asserts;

import models.get_all_author_books_xml.GetAllAuthorBooksXmlResponse;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AssertGetAllAuthorBooksXml {

    public static void assertionGettingAllAuthorBooksXml(GetAllAuthorBooksXmlResponse books) {
        assertThat(books).isNotNull();
    }
}
