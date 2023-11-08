package steps.asserts;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import entity.AuthorTable;
import entity.BookTable;
import java.util.List;
import models.add_new_author.SaveNewAuthorRequest;
import models.get_all_author_books.GetAllAuthorBooksResponse;
import models.get_all_author_books_xml.GetAllAuthorBooksXmlResponse;
import org.joda.time.DateTime;
import steps.data_base_steps.author_table.SqlMethodsAuthorTable;
import steps.data_base_steps.book_table.SqlMethodsBookTable;

public class AssertSql {

    public void assertListSize(List<BookTable> books, int size) {
        assertEquals(size, books.size());
    }

    public void assertAuthorExist(SaveNewAuthorRequest expectedAuthor, Long expectedAuthorId) {
        SqlMethodsAuthorTable sqlMethodsAuthorTable = new SqlMethodsAuthorTable();
        AuthorTable actualAuthor = sqlMethodsAuthorTable.findAuthor(expectedAuthor.getFirstName(),
            expectedAuthor.getFamilyName());

        assertEquals(expectedAuthor.getFirstName(), actualAuthor.getFirstName());
        assertEquals(expectedAuthor.getFamilyName(), actualAuthor.getFamilyName());
        assertEquals(expectedAuthorId, actualAuthor.getId());
    }

    public void assertBookExist(String expectedBookTitle, Long expectedAuthorId,
        Long expectedBookId) {
        SqlMethodsBookTable sqlMethodsBookTableTable = new SqlMethodsBookTable();
        List<BookTable> actualBooksList = sqlMethodsBookTableTable.findBookByTitle(
            expectedBookTitle);

        BookTable actualBook = actualBooksList.get(0);

        assertEquals(expectedBookTitle, actualBook.getBookTitle());
        assertEquals(expectedAuthorId, actualBook.getAuthorId());
        assertEquals(expectedBookId, actualBook.getId());
    }

    public void assertBookNotExist(String expectedBookTitle) {
        SqlMethodsBookTable sqlMethodsBookTableTable = new SqlMethodsBookTable();
        List<BookTable> actualBooksList = sqlMethodsBookTableTable.findBookByTitle(
            expectedBookTitle);

        assertTrue(actualBooksList.isEmpty());
    }

    public void assertBookListExist(List<GetAllAuthorBooksResponse> books, Long authorId,
        DateTime timeStamp) {
        SqlMethodsBookTable sqlMethodsBookTableTable = new SqlMethodsBookTable();
        List<BookTable> actualBooksList = sqlMethodsBookTableTable.findBookByAuthorId(authorId);

        GetAllAuthorBooksResponse expectedBook = books.get(0);
        BookTable actualBook = actualBooksList.get(0);

        assertEquals(expectedBook.getAuthor().getId(), actualBook.getAuthorId());
        assertEquals(expectedBook.getBookTitle(), actualBook.getBookTitle());
        assertThat(expectedBook.getUpdated()).isEqualToIgnoringMillis(timeStamp.toDate());

    }

    public void assertBookListEmpty(Long authorId) {
        SqlMethodsBookTable sqlMethodsBookTableTable = new SqlMethodsBookTable();
        List<BookTable> actualBooksList = sqlMethodsBookTableTable.findBookByAuthorId(authorId);

        assertTrue(actualBooksList.isEmpty());
    }

    public void assertBookListExistXml(GetAllAuthorBooksXmlResponse books, Long authorId,
        DateTime timeStamp) {
        SqlMethodsBookTable sqlMethodsBookTableTable = new SqlMethodsBookTable();
        List<BookTable> actualBooksList = sqlMethodsBookTableTable.findBookByAuthorId(authorId);

        GetAllAuthorBooksResponse expectedBook = books.getBooks().get(0);
        BookTable actualBook = actualBooksList.get(0);

        assertEquals(expectedBook.getAuthor().getId(), actualBook.getAuthorId());
        assertEquals(expectedBook.getBookTitle(), actualBook.getBookTitle());
        assertThat(expectedBook.getUpdated()).isEqualToIgnoringMillis(timeStamp.toDate());
    }
}
