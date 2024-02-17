package helpers;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.ParameterExpression;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import entity.City;
import entity.Country;

import java.util.List;

public class CountryHelper {

    private SessionFactory sessionFactory;

    public CountryHelper() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    public List<Country> getCountryList() {
        Session session = sessionFactory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Country> criteriaQuery = criteriaBuilder.createQuery(Country.class);
        criteriaQuery.from(Country.class);

        Query query = session.createQuery(criteriaQuery);
        List<Country> countries = query.getResultList();
        session.close();
        return countries;
    }

    public Country getCountry(String code) {
        Session session = sessionFactory.openSession();
        return session.get(Country.class, code);
    }

    public Country addCountry(Country country) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.persist(country);
        session.getTransaction().commit();
        session.close();
        return country;
    }

    public void deleteCountryByCode(String countryCode) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaDelete<Country> deleteQuery = criteriaBuilder.createCriteriaDelete(Country.class);
            Root<Country> root = deleteQuery.from(Country.class);

            deleteQuery.where(criteriaBuilder.equal(root.get("code"), countryCode));

            Query query = session.createQuery(deleteQuery);
            int deletedCount = query.executeUpdate();

            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
