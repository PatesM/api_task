package steps.data_base_steps;

import static configurations.LibraryDatabaseConfiguration.getCommit;
import static configurations.LibraryDatabaseConfiguration.session;

import entity.BookTable;
import java.util.List;

public class SqlMethods {

    public static List<BookTable> findAll() {
        final String hql = """
            SELECT b FROM book b
            """;

        return session.createQuery(hql, BookTable.class).getResultList();
    }

    public static void deleteAll() {
        final String hql = """
            DELETE FROM book
            """;

        getCommit(hql);
    }

    public static void insertBook(String bookTitle, Long authorId) { // вставить переменные в запрос
        final String hql = """
            INSERT INTO book (book_title, author_id) VALUES('Кладбище', 2)
            """;

        getCommit(hql);
    }

    public static List<BookTable> findBook(String bookTitle) {
        final String hql = """
            SELECT b FROM book b
            """;

        return session.createQuery(hql, BookTable.class).getResultList();
    }
}
