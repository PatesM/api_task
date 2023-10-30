package steps.asserts;

import models.get_all_author_books.GetAllAuthorBooksResponse;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AssertGetAllAuthorBooks {
    public static void assertionGettingAllAuthorBooks(List<GetAllAuthorBooksResponse> books) {
        assertThat(books).isNotEmpty();
    }
}
