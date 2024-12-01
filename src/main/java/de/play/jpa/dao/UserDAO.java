package de.play.jpa.dao;

import de.play.controller.PasswordHash;
import de.play.jpa.entity.User;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class UserDAO {

    @PersistenceContext(name = "PersistenceUnitPlayground")
    private EntityManager entityManager;

    @Transactional
    public User create(User user) {
        try {
            user.setPassword(PasswordHash.createHashedPassword(user.getPassword()));
            entityManager.persist(user);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            Logger.getLogger(UserDAO.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }

    public List<User> list() {
        return entityManager.createQuery("SELECT u FROM User u", User.class
        ).getResultList();
    }

    public Optional<User> findUser(Long id) {
        return Optional.ofNullable(entityManager.find(User.class,
                id));
    }

    public Optional<User> findUser(String email, String password) {
        try {
            TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE email = :email AND password = :password", User.class);
            query.setParameter("email", email);
            query.setParameter("password", PasswordHash.createHashedPassword(password));
            User user = query.getSingleResult();
            return Optional.of(user);

        } catch (NoResultException ignore) {
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            Logger.getLogger(UserDAO.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return Optional.empty();
    }

    @Transactional
    public User update(User user) {
        return entityManager.merge(user);
    }

    @Transactional
    public void delete(User user) {
        entityManager.remove(user);
    }

}
