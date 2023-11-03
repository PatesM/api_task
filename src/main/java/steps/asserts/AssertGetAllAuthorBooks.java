package steps.asserts;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import models.add_new_author.SaveNewAuthorRequest;
import models.get_all_author_books.GetAllAuthorBooksResponse;

public class AssertGetAllAuthorBooks {

    public static void assertionGettingAllAuthorBooksPositiveResult(
        List<GetAllAuthorBooksResponse> books, String bookTitle, Long authorId,
        SaveNewAuthorRequest authorRequest) {
        assertThat(books.get(0).getBookTitle()).isEqualTo(bookTitle);
        assertThat(books.get(0).getAuthor().getId()).isEqualTo(authorId);
        assertThat(books.get(0).getAuthor().getFirstName()).isEqualTo(authorRequest.getFirstName());
        assertThat(books.get(0).getAuthor().getFamilyName()).isEqualTo(
            authorRequest.getFamilyName());
    }

    public static void assertionGettingAllAuthorBooksEmptyList(
        List<GetAllAuthorBooksResponse> books) {
        assertTrue(books.isEmpty());
    }
}
