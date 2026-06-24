package it.sfb.backend.shop;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ShopRepository implements PanacheRepositoryBase<Shop, UUID> {

    public Shop findByName(String name) {
        return find("name", name).firstResult();
    }

    public List<Shop> findByCity() {
        return findAll(Sort.by("city")).stream().toList();
    }
}
