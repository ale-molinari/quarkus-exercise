package it.sfb.backend.customer;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class CustomerRepository implements PanacheRepositoryBase<Customer, UUID> {

    public Customer findByName(String name) {
        return find("name", name).firstResult();
    }

    public Customer findById(UUID id) {
        return find("id", id).firstResult();
    }
}
