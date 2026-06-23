package it.sfb.backend.customer;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Path("/customer")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CustomerResource {

    @Inject
    CustomerRepository customerRepository;

    @GET
    public List<Customer> getAllCustomers(){
        return customerRepository.listAll();
    }

    @GET
    @Path("{id}")
    public Customer getById(UUID id) {
        return customerRepository.findById(id);
    }

    @POST
    @Transactional
    public Response createCustomer(Customer customer) {
        if (customer.getName() != null || !customer.getName().isEmpty()) {
            customer.setName(customer.getName().toLowerCase(Locale.ROOT));
        }
        customerRepository.persist(customer);
        return Response.created(URI.create("/customer/" + customer.getId())).build();
    }

    @PUT
    @Transactional
    @Path("/{id}")
    public Customer updateCustomer(UUID id, Customer customer) {
        Customer entity = customerRepository.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }

        entity.setName(customer.getName());
        entity.setEmail(customer.getEmail());
        entity.setBirthDate(customer.getBirthDate());
        return entity;
    }

    @PUT
    @Transactional
    @Path("{Id}")
    public Customer updateCustomer(@PathParam("id") UUID id, EStatus status) {
        Customer entity = customerRepository.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }
        entity.setStatus(status);
        return entity;
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    public void deleteCustomer(UUID id) {
        Customer entity = customerRepository.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }
        customerRepository.delete(entity);
    }

    @GET
    @Path("/search/{name}")
    public Customer search(String name) {
        return customerRepository.findByName(name);
    }
}
