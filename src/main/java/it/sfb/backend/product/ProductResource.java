package it.sfb.backend.product;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;

import java.net.URI;
import java.util.List;
import java.util.Locale;
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
    @Path("/price/ascending")
    public List<Product> sortByAscendingPrice(){
        return productRepository.sortByAscendingPrice();
    }

    @POST
    @Transactional
    public Response addProduct(Product product){
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        Product newProduct = productRepository.addProduct(product);
        return Response.created(URI.create("/product/" + newProduct.getName()))
                .entity(newProduct)
                .build();
    }

    @PUT
    @Transactional
    @Path("/{id}")
    public Product updateProduct(UUID id, Product product){
        Product entity = productRepository.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }
        return productRepository.updateProduct(entity, product);
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    public Response deleteProduct(UUID id){
        Product entity = productRepository.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }
        productRepository.delete(entity);
        return Response.ok().build();
    }
}
