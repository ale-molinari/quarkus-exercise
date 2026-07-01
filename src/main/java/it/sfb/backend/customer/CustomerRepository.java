package it.sfb.backend.customer;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import it.sfb.backend.IService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import java.util.Locale;
import java.util.UUID;

@ApplicationScoped
public class CustomerRepository implements IService<Customer, UUID> {

    private static final Logger log = Logger.getLogger(CustomerRepository.class);

    @Transactional
    public Customer createCustomer(Customer customer) {
        customer.setName(customer.getName().toLowerCase(Locale.ROOT));
        persist(customer);
        log.info("Customer created: " + customer.getName());
        return customer;
    }

    @Transactional
    public Customer updateCustomer(Customer entity, Customer customer) {
        entity.setName(customer.getName());
        entity.setEmail(customer.getEmail());
        entity.setBirthDate(customer.getBirthDate());
        log.info("Customer updated: " + customer.getName());
        return entity;
    }

    @Transactional
    public void deleteCustomer(Customer customer) {
        delete(customer);
        log.info("Customer deleted: " + customer.getName());
    }
}
