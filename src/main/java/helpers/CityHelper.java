package helpers;

import entity.City;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.List;

public class CityHelper {

    private SessionFactory sessionFactory;
    public CityHelper(){
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    public List<City> getCityList(){
        Session session = sessionFactory.openSession();

        session.get(City.class, 1l);

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery criteriaQuery =criteriaBuilder.createQuery(City.class);

        Root<City>  root =criteriaQuery.from(City.class);
        ParameterExpression<Integer> param=  criteriaBuilder.parameter(Integer.class, "population");

        criteriaQuery.select(root).
                where(criteriaBuilder.greaterThan(root.get("population"), param));

        Query query = session.createQuery(criteriaQuery);
        query.setParameter("population", 1000000);
        List<City> cities = query.getResultList();
        session.close();
        return cities;
    }

    public City getCity(String name){
        return null;
    }

    public City addCity(City city){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.persist(city);
        session.getTransaction().commit();
        session.close();
        return city;
    }

    public void deleteCityByName(String cityName) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaDelete<City> deleteQuery = criteriaBuilder.createCriteriaDelete(City.class);
            Root<City> root = deleteQuery.from(City.class);

            deleteQuery.where(criteriaBuilder.equal(root.get("name"), cityName));

            Query query = session.createQuery(deleteQuery);
            int deletedCount = query.executeUpdate();

            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }}
