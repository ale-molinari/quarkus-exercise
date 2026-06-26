package it.sfb.backend.customer;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Locale;
import java.util.UUID;

@ApplicationScoped
public class CustomerRepository implements PanacheRepositoryBase<Customer, UUID> {

    public Customer findByName(String name) {
        return find("name", name).firstResult();
    }

    public Customer findById(UUID id) {
        return find("id", id).firstResult();
    }

    public Customer create(Customer customer) {
        if (customer.getName() != null || !customer.getName().isEmpty()) {
            customer.setName(customer.getName().toLowerCase(Locale.ROOT));
        }
        persist(customer);
        return customer;
    }

    public Customer update(Customer entity, Customer customer) {
        entity.setName(customer.getName());
        entity.setEmail(customer.getEmail());
        entity.setBirthDate(customer.getBirthDate());
        return entity;
    }
}
