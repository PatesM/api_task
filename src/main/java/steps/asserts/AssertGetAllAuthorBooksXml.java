package steps.asserts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import models.add_new_author.SaveNewAuthorRequest;
import models.get_all_author_books_xml.GetAllAuthorBooksXmlResponse;

public class AssertGetAllAuthorBooksXml {

    public static void assertionGettingAllAuthorBooksXmlPositiveResult(
        GetAllAuthorBooksXmlResponse books, String bookTitle, Long authorId,
        SaveNewAuthorRequest authorRequest) {
        assertEquals(books.getBooks().get(0).getAuthor().getId(), authorId);
        assertEquals(books.getBooks().get(0).getAuthor().getFirstName(),
            authorRequest.getFirstName());
        assertEquals(books.getBooks().get(0).getAuthor().getFamilyName(),
            authorRequest.getFamilyName());
        assertEquals(books.getBooks().get(0).getBookTitle(), bookTitle);
    }

    public static void assertionGettingAllAuthorBooksXmlEmptyList(
        GetAllAuthorBooksXmlResponse books) {
        assertTrue(books.getBooks().isEmpty());
    }
}
