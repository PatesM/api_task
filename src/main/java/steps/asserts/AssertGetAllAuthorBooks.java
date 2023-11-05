package steps.asserts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import models.add_new_author.SaveNewAuthorRequest;
import models.get_all_author_books.GetAllAuthorBooksResponse;

public class AssertGetAllAuthorBooks {

    public static void assertionGettingAllAuthorBooksPositiveResult(
        List<GetAllAuthorBooksResponse> books, String bookTitle, Long authorId,
        SaveNewAuthorRequest authorRequest) {
        assertEquals(books.get(0).getBookTitle(), bookTitle);
        assertEquals(books.get(0).getAuthor().getId(), authorId);
        assertEquals(books.get(0).getAuthor().getFirstName(), authorRequest.getFirstName());
        assertEquals(books.get(0).getAuthor().getFamilyName(), authorRequest.getFamilyName());
    }

    public static void assertionGettingAllAuthorBooksEmptyList(
        List<GetAllAuthorBooksResponse> books) {
        assertTrue(books.isEmpty());
    }
}
