package helpers;

import entity.City;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Collections;
import java.util.List;

public class CityHelper {

    private final SessionFactory sessionFactory;
    private final CriteriaBuilder criteriaBuilder;

    public CityHelper() {
        sessionFactory = HibernateUtil.getSessionFactory();
        criteriaBuilder = sessionFactory.getCriteriaBuilder();
    }

    public List<City> getCityList() {
        try (Session session = sessionFactory.openSession()) {
            CriteriaQuery<City> criteriaQuery = criteriaBuilder.createQuery(City.class);
            Root<City> root = criteriaQuery.from(City.class);

            ParameterExpression<Integer> param = criteriaBuilder.parameter(Integer.class, "population");

            criteriaQuery.select(root)
                    .where(criteriaBuilder.greaterThan(root.get("population"), param));

            Query query = session.createQuery(criteriaQuery);
            query.setParameter("population", 1000000);

            return query.getResultList();
        } catch (Exception e) {
            // Log or rethrow a custom exception
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public City getCity(String name) {
        // Implement this method as needed
        return null;
    }

    public City addCity(City city) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(city);
            session.getTransaction().commit();
            return city;
        } catch (Exception e) {
            // Log or rethrow a custom exception
            e.printStackTrace();
            return null;
        }
    }

    public void deleteCityByName(String cityName) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            CriteriaDelete<City> deleteQuery = criteriaBuilder.createCriteriaDelete(City.class);
            Root<City> root = deleteQuery.from(City.class);

            deleteQuery.where(criteriaBuilder.equal(root.get("name"), cityName));

            Query query = session.createQuery(deleteQuery);
            int deletedCount = query.executeUpdate();

            session.getTransaction().commit();
        } catch (Exception e) {
            // Log or rethrow a custom exception
            e.printStackTrace();
        }
    }
}
