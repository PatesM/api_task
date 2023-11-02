package steps.asserts;

import static org.assertj.core.api.Assertions.assertThat;

import models.get_all_author_books_xml.GetAllAuthorBooksXmlResponse;

public class AssertGetAllAuthorBooksXml {

    public static void assertionGettingAllAuthorBooksXmlPositiveResult(
        GetAllAuthorBooksXmlResponse books, String bookTitle, Long authorId, String firstName,
        String familyName) {
        assertThat(books.getBooks().get(0).getAuthor().getId()).isEqualTo(authorId);
        assertThat(books.getBooks().get(0).getAuthor().getFirstName()).isEqualTo(firstName);
        assertThat(books.getBooks().get(0).getAuthor().getFamilyName()).isEqualTo(familyName);
        assertThat(books.getBooks().get(0).getBookTitle()).isEqualTo(bookTitle);
    }

    public static void assertionGettingAllAuthorBooksXmlEmptyList(
        GetAllAuthorBooksXmlResponse books) {
        assertThat(books.getBooks()).isEmpty();
    }
}
