package de.play.restServer;

//@Path("/rest")
import de.play.jpa.entity.User;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

//@ApplicationScoped
@Path("rest/users")
//@RegisterRestClient(baseUri = "http://localhost:8080")
@RegisterRestClient(configKey = "rest.UsersClient")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RegisterProvider(RestClientLoggingFilter.class)
public interface UsersClient {

    @GET
    List<User> getUsers();

    @GET
    @Path("/{userId}")
    User getUser(@PathParam("userId") String userId);

    @POST
    @Retry(maxRetries = 1)  // Maximal 3 Versuche bei Fehlern
    @Fallback(fallbackMethod = "fallbackCreateUser")  // Fallback-Methode
    Response createUser(User user);

    default Response fallbackCreateUser(User user) {
        System.out.println("Fallback!");
        return Response.status(204).build();
    }

}
