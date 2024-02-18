package helpers;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {

    private static volatile SessionFactory sessionFactory;

    private HibernateUtil() {
        // private constructor to prevent instantiation
    }

    private static SessionFactory buildSessionFactory() {
        if (sessionFactory == null) {
            synchronized (HibernateUtil.class) {
                if (sessionFactory == null) {
                    final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                            .configure()
                            .build();
                    try {
                        sessionFactory = new MetadataSources(registry)
                                .buildMetadata()
                                .buildSessionFactory();
                    } catch (Exception e) {
                        // Log the exception or throw a custom exception
                        throw new RuntimeException("Failed to initialize Hibernate SessionFactory", e);
                    } finally {
                        StandardServiceRegistryBuilder.destroy(registry);
                    }
                }
            }
        }
        return sessionFactory;
    }

    public static SessionFactory getSessionFactory() {
        return buildSessionFactory();
    }
}
