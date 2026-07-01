package it.sfb.backend.shop;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
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
        return shopRepository.findByIdOrThrow(id);
    }

    @GET
    @Path("/search/{name}")
    public Shop getShopByName(String name) {
        return shopRepository.findByName(name);
    }

    @POST
    public Response createShop(@Valid Shop shop) {
        Shop newShop = shopRepository.createShop(shop);
        return Response.created(URI.create("/shop/" + newShop.getName()))
                .entity(newShop)
                .build();
    }

    @PUT
    @Path("/{id}")
    public Shop updateShop(UUID id, @Valid Shop shop) {
        Shop entity = shopRepository.findByIdOrThrow(id);
        return shopRepository.updateShop(entity, shop);
    }

    @DELETE
    @Path("/{id}")
    public Response deleteShop(UUID id) {
        Shop entity = shopRepository.findByIdOrThrow(id);
        shopRepository.deleteShop(entity);
        return Response.ok().build();
    }
}
