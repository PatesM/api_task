package steps.asserts;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import models.get_all_author_books.GetAllAuthorBooksResponse;

public class AssertGetAllAuthorBooks {

    public static void assertionGettingAllAuthorBooksPositiveResult(
        List<GetAllAuthorBooksResponse> books, String bookTitle, Long authorId, String firstName,
        String familyName) {
        assertThat(books.get(0).getBookTitle()).isEqualTo(bookTitle);
        assertThat(books.get(0).getAuthor().getId()).isEqualTo(authorId);
        assertThat(books.get(0).getAuthor().getFirstName()).isEqualTo(firstName);
        assertThat(books.get(0).getAuthor().getFamilyName()).isEqualTo(familyName);
    }

    public static void assertionGettingAllAuthorBooksEmptyList(
        List<GetAllAuthorBooksResponse> books) {
        assertThat(books).isEmpty();
    }
}
