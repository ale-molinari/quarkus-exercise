package it.sfb.backend.customer;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;
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

    @GET
    @Path("/search/{name}")
    public Customer search(String name) {
        return customerRepository.findByName(name);
    }

    @POST
    public Response createCustomer(@Valid Customer customer) {
        Customer newCustomer = customerRepository.createCustomer(customer);
        return Response.created(URI.create("/customer/" + newCustomer.getCustomerId()))
                .entity(newCustomer)
                .build();
    }

    @PUT
    @Path("/{id}")
    public Customer updateCustomer(UUID id, @Valid Customer customer) {
        Customer entity = customerRepository.findByIdOrThrow(id);
        return customerRepository.updateCustomer(entity, customer);
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCustomer(UUID id) {
        Customer entity = customerRepository.findByIdOrThrow(id);
        customerRepository.deleteCustomer(entity);
        return Response.ok().build();
    }
}
