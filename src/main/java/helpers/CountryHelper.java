package helpers;

import jakarta.persistence.criteria.*;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import entity.Country;

import java.util.Collections;
import java.util.List;

public class CountryHelper {

    private final SessionFactory sessionFactory;
    private final CriteriaBuilder criteriaBuilder;

    public CountryHelper() {
        sessionFactory = HibernateUtil.getSessionFactory();
        criteriaBuilder = sessionFactory.getCriteriaBuilder();
    }

    public List<Country> getCountryList() {
        try (Session session = sessionFactory.openSession()) {
            CriteriaQuery<Country> criteriaQuery = criteriaBuilder.createQuery(Country.class);
            criteriaQuery.from(Country.class);

            Query query = session.createQuery(criteriaQuery);
            return query.getResultList();
        } catch (Exception e) {
            // Log or rethrow a custom exception
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public Country getCountry(String code) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Country.class, code);
        } catch (Exception e) {
            // Log or rethrow a custom exception
            e.printStackTrace();
            return null;
        }
    }

    public Country addCountry(Country country) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(country);
            session.getTransaction().commit();
            return country;
        } catch (Exception e) {
            // Log or rethrow a custom exception
            e.printStackTrace();
            return null;
        }
    }

    public void deleteCountryByCode(String countryCode) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            CriteriaDelete<Country> deleteQuery = criteriaBuilder.createCriteriaDelete(Country.class);
            Root<Country> root = deleteQuery.from(Country.class);

            deleteQuery.where(criteriaBuilder.equal(root.get("code"), countryCode));

            Query query = session.createQuery(deleteQuery);
            int deletedCount = query.executeUpdate();

            session.getTransaction().commit();
        } catch (Exception e) {
            // Log or rethrow a custom exception
            e.printStackTrace();
        }
    }
}
