package de.play.cache;

import de.play.restServer.Product;
import de.play.restServer.ProductsClient;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class ProductsCache extends TempDirDataCache<Map<String, Product>> {

    @Inject
    @RestClient
    ProductsClient productsClient;

    Map<String, Product> cache = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        cache = super.findTempCache();
    }

    public Product findProcuct(String id) {
        return cache.get(id);
    }

    public Collection<Product> getAll() {
        return cache.values();
    }

    @Override
    public String getTempFileName() {
        return "products.dat";
    }

    @Override
    public Map<String, Product> loadFromNetwork() {
        return productsClient.getProducts().stream()
                .collect(Collectors.toMap(p -> p.id(), Function.identity()));
    }

}
