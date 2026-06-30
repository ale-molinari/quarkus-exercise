package it.sfb.backend.product;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class ProductRepository implements PanacheRepositoryBase<Product, UUID> {

    private static final Logger log = Logger.getLogger(ProductRepository.class);

    public List<Product> sortByAscendingPrice() {
        PanacheQuery<Product> products = findAll(Sort.by("price").ascending());
        return products.list();
    }

    public Product findByIdOrThrow(UUID id) {
        return findByIdOptional(id).orElseThrow(
                () -> new IllegalArgumentException("Product not found with id: " + id)
        );
    }

    public Product findByName(String name) {
        return findByNameOptional(name).orElseThrow(
                () -> new IllegalArgumentException("Product not found with name: " + name)
        );
    }

    public Optional<Product> findByNameOptional(String name) {
        return find("name", name).firstResultOptional();
    }

    @Transactional
    public Product createProduct(Product product) {
        product.setName(product.getName().toLowerCase(Locale.ROOT));
        persist(product);
        log.info("Product created: " + product.getName());
        return product;
    }

    @Transactional
    public Product updateProduct(Product entity, Product product) {
        entity.setName(product.getName());
        entity.setPrice(product.getPrice());
        entity.setQuantity(product.getQuantity());
        entity.setAvailability(product.getAvailability());
        log.info("Product updated: " + product.getName());
        return entity;
    }

    @Transactional
    public void deleteProduct(Product product) {
        delete(product);
        log.info("Product deleted: " + product.getName());
    }
}
