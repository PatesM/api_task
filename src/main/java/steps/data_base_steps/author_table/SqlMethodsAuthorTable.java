package steps.data_base_steps.author_table;

import static configurations.LibraryDatabaseConfiguration.getSession;

import entity.AuthorTable;
import entity.BookTable;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

public class SqlMethodsAuthorTable {

    Session session;

    public SqlMethodsAuthorTable() {
        session = getSession();
    }

    public void insertAuthor(String firstName, String familyName, String secondName, String birthDate) {
        final String hql = """
            INSERT INTO author (first_name, family_name, second_name, birth_date) 
            VALUES(:firstName, :familyName, :secondName, :birthDate)
            """;

        Transaction transaction = session.beginTransaction();
        NativeQuery<AuthorTable> nativeQuery = session.createNativeQuery(hql, AuthorTable.class);
        nativeQuery.setParameter("firstName", firstName);
        nativeQuery.setParameter("familyName", familyName);
        nativeQuery.setParameter("secondName", secondName);
        nativeQuery.setParameter("birthDate", birthDate);
        nativeQuery.executeUpdate();
        transaction.commit();
    }

    public AuthorTable findAuthor(String firstName, String familyName) {
        final String hql = """
            SELECT * FROM author 
            WHERE (first_name, family_name) = (:firstName, :familyName)
            """;

        NativeQuery<AuthorTable> nativeQuery = session.createNativeQuery(hql, AuthorTable.class);
        nativeQuery.setParameter("firstName", firstName);
        nativeQuery.setParameter("familyName", familyName);
        return nativeQuery.getSingleResult();
    }


}
