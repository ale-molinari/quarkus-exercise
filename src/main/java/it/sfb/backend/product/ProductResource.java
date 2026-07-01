package it.sfb.backend.product;

import jakarta.inject.Inject;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.validation.Valid;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Path("/product")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProductResource {

    @Inject
    ProductRepository productRepository;

    @GET
    public List<Product> getAllProducts(){
        return productRepository.listAll();
    }

    @GET
    @Path("/id")
    public Product getProductById(UUID id){
        return productRepository.findByIdOrThrow(id);
    }

    @GET
    @Path("/price/ascending")
    public List<Product> sortByAscendingPrice(){
        return productRepository.sortByAscendingPrice();
    }

    @POST
    public Response createProduct(@Valid Product product){
        Product newProduct = productRepository.createProduct(product);
        return Response.created(URI.create("/product/" + newProduct.getName()))
                .entity(newProduct)
                .build();
    }

    @PUT
    @Path("/{id}")
    public Product updateProduct(UUID id, @Valid Product product){
        Product entity = productRepository.findByIdOrThrow(id);
        return productRepository.updateProduct(entity, product);
    }

    @DELETE
    @Path("/{id}")
    public Response deleteProduct(UUID id){
        Product entity = productRepository.findByIdOrThrow(id);
        productRepository.deleteProduct(entity);
        return Response.ok().build();
    }
}
