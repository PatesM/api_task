package steps.asserts;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import entity.AuthorTable;
import java.util.List;
import models.add_new_author.SaveNewAuthorRequest;
import models.get_all_author_books.GetAllAuthorBooksResponse;
import org.joda.time.DateTime;

public class AssertGetAllAuthorBooks {

    public void assertionGettingAllAuthorBooksPositiveResult(
        List<GetAllAuthorBooksResponse> books, String bookTitle, DateTime timeStamp, Long authorId,
        SaveNewAuthorRequest authorRequest) {
        AuthorTable author = books.get(0).getAuthor();

        assertEquals(books.get(0).getBookTitle(), bookTitle);
        assertThat(books.get(0).getUpdated()).isEqualToIgnoringMillis(timeStamp.toDate());
        assertEquals(author.getId(), authorId);
        assertEquals(author.getFirstName(), authorRequest.getFirstName());
        assertEquals(author.getFamilyName(), authorRequest.getFamilyName());
        assertEquals(author.getSecondName(), authorRequest.getSecondName());
        assertEquals(author.getBirthDate(), authorRequest.getBirthDate());
    }

    public void assertionGettingAllAuthorBooksEmptyList(
        List<GetAllAuthorBooksResponse> books) {
        assertTrue(books.isEmpty());
    }
}
