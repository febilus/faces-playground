package de.play.rest;

import de.play.jpa.dao.UserDAO;
import de.play.jpa.entity.User;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;

@RequestScoped
@Path("users")
public class UserResource {

    @Inject
    UserDAO userDAO;

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    public List<User> getUsers() {
        return userDAO.list();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    public User create(User user) throws IOException {
        if (Math.random() < 0.5) {
            System.out.println("TEST Fail");
            throw new RuntimeException("Simulate Failure Test");
        }
        return userDAO.create(user);
    }

}
