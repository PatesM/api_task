package steps.asserts;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import entity.AuthorTable;
import models.add_new_author.SaveNewAuthorRequest;
import models.get_all_author_books_xml.GetAllAuthorBooksXmlResponse;
import org.joda.time.DateTime;

public class AssertGetAllAuthorBooksXml {


    public void assertionGettingAllAuthorBooksXmlPositiveResult(
        GetAllAuthorBooksXmlResponse books, String bookTitle, DateTime timeStamp, Long authorId,
        SaveNewAuthorRequest authorRequest) {
        AuthorTable author = books.getBooks().get(0).getAuthor();

        assertEquals(author.getId(), authorId);
        assertEquals(author.getFirstName(), authorRequest.getFirstName());
        assertEquals(author.getFamilyName(), authorRequest.getFamilyName());
        assertEquals(author.getSecondName(), authorRequest.getSecondName());
        assertEquals(author.getBirthDate(), authorRequest.getBirthDate());
        assertEquals(books.getBooks().get(0).getBookTitle(), bookTitle);
        assertThat(books.getBooks().get(0).getUpdated()).isEqualToIgnoringMillis(
            timeStamp.toDate());
    }

    public void assertionGettingAllAuthorBooksXmlEmptyList(
        GetAllAuthorBooksXmlResponse books) {
        assertTrue(books.getBooks().isEmpty());
    }
}
