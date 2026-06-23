package it.sfb.backend.product;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ProductRepository implements PanacheRepository<Product> {

    public List<Product> sortByAscendingPrice() {
        PanacheQuery<Product> products = findAll(Sort.by("price").ascending());
        return products.list();
    }

    public Product findById(UUID id) {
        return find("id", id).firstResult();
    }
}
