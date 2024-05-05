package de.play.jpa.dao;

import de.play.jpa.entity.ZipStreet;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;

@Stateless
public class ZipStreetDAO {

    @PersistenceContext(name = "PersistenceUnitPlayground")
    private EntityManager entityManager;

    public List<ZipStreet> findByZipOrStreet(String zip, String street) {
        TypedQuery<ZipStreet> query = entityManager.createQuery("SELECT s FROM ZipStreet AS s WHERE zip = :zip OR streetNormalized LIKE :street", ZipStreet.class);
        query.setParameter("zip", zip);
        query.setParameter("street", "%" + street + "%");
        return query.getResultList();
    }

}
