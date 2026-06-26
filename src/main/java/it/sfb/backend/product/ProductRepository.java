package it.sfb.backend.product;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

@ApplicationScoped
public class ProductRepository implements PanacheRepositoryBase<Product, UUID> {

    public List<Product> sortByAscendingPrice() {
        PanacheQuery<Product> products = findAll(Sort.by("price").ascending());
        return products.list();
    }

    public Product findById(UUID id) {
        return find("id", id).firstResult();
    }

    public Product findByName(String name) {
        return find("name", name).firstResult();
    }

    public Product addProduct(Product product) {
        if (product.getName() != null || !product.getName().isEmpty()) {
            product.setName(product.getName().toLowerCase(Locale.ROOT));
        }
        persist(product);
        return product;
    }

    public Product updateProduct(Product entity, Product product) {
        entity.setName(product.getName());
        entity.setPrice(product.getPrice());
        entity.setQuantity(product.getQuantity());
        entity.setAvailability(product.getAvailability());
        return entity;
    }
}
