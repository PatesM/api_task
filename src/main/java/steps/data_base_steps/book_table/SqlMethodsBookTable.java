package steps.data_base_steps.book_table;

import static configurations.LibraryDatabaseConfiguration.getSession;

import entity.BookTable;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

public class SqlMethodsBookTable {

    Session session;

    public SqlMethodsBookTable() {
        session = getSession();
    }

    public List<BookTable> findAllBooks() {
        final String hql = """
            SELECT * FROM book
            """;

        return session.createNativeQuery(hql, BookTable.class).getResultList();
    }

    public void deleteAllBooks() {
        final String hql = "DELETE FROM book";

        Transaction transaction = session.beginTransaction();
        session.createNativeQuery(hql, BookTable.class).executeUpdate();
        transaction.commit();
    }

    public void deleteBookByTitle(String bookTitle) {
        final String hql = """
            DELETE FROM book 
            where book_title = :bookTitle
            """;

        Transaction transaction = session.beginTransaction();
        NativeQuery<BookTable> nativeQuery = session.createNativeQuery(hql, BookTable.class);
        nativeQuery.setParameter("bookTitle", bookTitle).executeUpdate();
        transaction.commit();
    }

    public void insertBook(String bookTitle, long authorId) {
        final String hql = """
            INSERT INTO book (book_title, author_id) 
            VALUES(:bookTitle, :authorId)
            """;

        Transaction transaction = session.beginTransaction();
        NativeQuery<BookTable> nativeQuery = session.createNativeQuery(hql, BookTable.class);
        nativeQuery.setParameter("bookTitle", bookTitle);
        nativeQuery.setParameter("authorId", authorId);
        nativeQuery.executeUpdate();
        transaction.commit();
    }

    public List<BookTable> findBookByTitle(String bookTitle) {
        final String hql = """
            SELECT * FROM book 
            WHERE book_title = :bookTitle
            """;

        NativeQuery<BookTable> nativeQuery = session.createNativeQuery(hql, BookTable.class);
        nativeQuery.setParameter("bookTitle", bookTitle);
        return nativeQuery.getResultList();
    }

    public List<BookTable> findBookByAuthorId(Long authorId) {
        final String hql = """
            SELECT * FROM book 
            WHERE author_id = :authorId
            """;

        NativeQuery<BookTable> nativeQuery = session.createNativeQuery(hql, BookTable.class);
        nativeQuery.setParameter("authorId", authorId);
        return nativeQuery.getResultList();
    }
}
