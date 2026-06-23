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
    Logger logger;

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
    public Response createProduct(Product product){
        if (product.getName() != null || !product.getName().isEmpty()) {
            product.setName(product.getName().toLowerCase(Locale.ROOT));
        }
        productRepository.persist(product);
        logger.info("Product created: " + product.getName());
        return Response.created(URI.create("/product/" + product.getId())).build();
    }

    @PUT
    @Transactional
    @Path("/{id}")
    public Product updateProduct(UUID id, Product product){
        Product entity = productRepository.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }
        entity.setName(product.getName());
        entity.setPrice(product.getPrice());
        entity.setQuantity(product.getQuantity());
        entity.setAvailability(product.getAvailability());
        logger.info("Product updated: " + entity.getName());
        return entity;
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    public void deleteProduct(UUID id){
        Product entity = productRepository.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }
        productRepository.delete(entity);
        logger.info("Product deleted: " + entity.getName());
    }
}
