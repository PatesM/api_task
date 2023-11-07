package configurations;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

public class LibraryDatabaseConfiguration {

    private static final SessionFactory sessionFactory = getSessionFactory();
    private static Session session;

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
}
