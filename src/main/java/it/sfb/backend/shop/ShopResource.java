package it.sfb.backend.shop;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Path("/shop")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ShopResource {

    @Inject
    ShopRepository shopRepository;

    @GET
    public List<Shop> getShop() {
        return shopRepository.listAll();
    }

    @GET
    @Path("/{id}")
    public Shop getShopById(UUID id) {
        return shopRepository.findById(id);
    }

    @GET
    @Path("/search/{name}")
    public Shop getShopByName(String name) {
        return shopRepository.findByName(name);
    }

    @GET
    @Path("/search/{city}")
    public List<Shop> getShopByCity() {
        return shopRepository.findByCity();
    }

    @POST
    @Transactional
    public Response addShop(Shop shop) {
        if (shop == null) {
            throw new IllegalArgumentException("Shop cannot be null");
        }
        Shop newShop = shopRepository.create(shop);
        return Response.created(URI.create("/shop/" + newShop.getName()))
                .entity(newShop)
                .build();
    }

    @PUT
    @Transactional
    @Path("/{id}")
    public Shop updateShop(UUID id, Shop shop) {
        Shop entity = shopRepository.findById(id);
        if (entity == null) {
            throw new NotFoundException();
        }
        return shopRepository.update(entity, shop);
    }

    @DELETE
    @Transactional
    @Path("/{id}")
    public void deleteShop(UUID id) {
        shopRepository.deleteById(id);
    }
}
