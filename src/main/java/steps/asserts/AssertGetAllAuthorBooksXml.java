package steps.asserts;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import models.add_new_author.SaveNewAuthorRequest;
import models.get_all_author_books_xml.GetAllAuthorBooksXmlResponse;

public class AssertGetAllAuthorBooksXml {

    public static void assertionGettingAllAuthorBooksXmlPositiveResult(
        GetAllAuthorBooksXmlResponse books, String bookTitle, Long authorId,
        SaveNewAuthorRequest authorRequest) {
        assertThat(books.getBooks().get(0).getAuthor().getId()).isEqualTo(authorId);
        assertThat(books.getBooks().get(0).getAuthor().getFirstName()).isEqualTo(
            authorRequest.getFirstName());
        assertThat(books.getBooks().get(0).getAuthor().getFamilyName()).isEqualTo(
            authorRequest.getFamilyName());
        assertThat(books.getBooks().get(0).getBookTitle()).isEqualTo(bookTitle);
    }

    public static void assertionGettingAllAuthorBooksXmlEmptyList(
        GetAllAuthorBooksXmlResponse books) {
        assertTrue(books.getBooks().isEmpty());
    }
}
