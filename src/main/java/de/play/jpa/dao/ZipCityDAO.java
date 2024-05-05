package de.play.jpa.dao;

import de.play.jpa.entity.ZipCity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;

@Stateless
public class ZipCityDAO {

    @PersistenceContext(name = "PersistenceUnitPlayground")
    private EntityManager entityManager;

    public List<ZipCity> findByZipOrCity(String zip, String city) {
        TypedQuery<ZipCity> query = entityManager.createQuery("SELECT z FROM ZipCity AS z WHERE zip = :zip OR cityNormalized LIKE :city", ZipCity.class);
        query.setParameter("zip", zip);
        query.setParameter("city", "%" + city + "%");
        return query.getResultList();
    }

}
