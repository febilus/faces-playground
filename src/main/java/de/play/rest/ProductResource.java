package de.play.rest;

import de.play.restServer.Product;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@RequestScoped
@Path("products")
public class ProductResource {

    @Path("all")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    public List<Product> getProducts() {

        return List.of(
                new Product("1", "Test 1", "Internal"),
                new Product("2", "Bryzen 2", "BMD"),
                new Product("3", "Test 3", "Abuz")
        );
    }

}
