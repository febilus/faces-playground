package de.play.views;

import de.play.cache.ProductsCache;
import de.play.restServer.Product;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.Collection;

@Named
@RequestScoped
public class ProductsView {

    @Inject
    ProductsCache productsCache;

    public Collection<Product> getPropducts() {
        return productsCache.getAll();
    }

}
