package it.sfb.backend.shop;

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
public class ShopRepository implements PanacheRepositoryBase<Shop, UUID> {

    private static final Logger log = Logger.getLogger(ShopRepository.class);

    public Shop findByIdOrThrow(UUID id) {
        return findByIdOptional(id).orElseThrow(
                () -> new IllegalArgumentException("Shop not found with id: " + id)
        );
    }

    public Shop findByName(String name) {
        return findByNameOptional(name).orElseThrow(
                () -> new IllegalArgumentException("Shop not found with name: " + name)
        );
    }

    public Optional<Shop> findByNameOptional(String name) {
        return find("name", name).firstResultOptional();
    }

    public List<Shop> findByCity() {
        return findAll(Sort.by("city")).stream().toList();
    }

    @Transactional
    public Shop createShop(Shop shop) {
        shop.setName(shop.getName().toLowerCase(Locale.ROOT));
        persist(shop);
        log.info("Shop created: " + shop.getName());
        return shop;
    }

    @Transactional
    public Shop updateShop(Shop entity, Shop shop) {
        entity.setName(shop.getName());
        entity.setCity(shop.getCity());
        log.info("Shop updated: " + shop.getName());
        return entity;
    }

    @Transactional
    public void deleteShop(Shop shop) {
        deleteById(shop.getShopId());
        log.info("Shop deleted: " + shop.getName());
    }
}
