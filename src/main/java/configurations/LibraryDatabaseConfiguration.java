package configurations;

import java.awt.print.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

public class LibraryDatabaseConfiguration {

    private static final SessionFactory sessionFactory = getSessionFactory();
    public static Session session;

    private LibraryDatabaseConfiguration() {
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure(
                    "hibernate.cfg.xml").build();

                Metadata metadata = new MetadataSources(serviceRegistry).getMetadataBuilder()
                    .build();

                return metadata.getSessionFactoryBuilder().build();
            } catch (Exception e) {
                System.out.println("Исключение!" + e);
            }
        }
        return sessionFactory;
    }

    public static Session getSession() {
        if (session == null) {
            try {
                session = getSessionFactory().openSession();
            } catch (Exception e) {
                System.out.println("Исключение!" + e);
            }
        }
        return session;
    }

    public static void getCommit(String hql) {
        Transaction transaction = session.beginTransaction();
        session.createNativeQuery(hql, Book.class).executeUpdate();
        transaction.commit();
    }
}
