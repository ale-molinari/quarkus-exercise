package it.sfb.backend.shop;

import it.sfb.backend.IService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;

import java.util.Locale;
import java.util.UUID;

@ApplicationScoped
public class ShopRepository implements IService<Shop, UUID> {

    private static final Logger log = Logger.getLogger(ShopRepository.class);

    @Transactional
    public Shop createShop(Shop shop) {
        shop.setName(shop.getName().toLowerCase(Locale.ROOT));
        persist(shop);
        log.info("Shop created: " + shop.getName());
        return shop;
    }

    @Transactional
    public Shop updateShop(Shop entity, Shop shop) {
        entity.setName(shop.getName());
        entity.setCity(shop.getCity());
        log.info("Shop updated: " + shop.getName());
        return entity;
    }

    @Transactional
    public void deleteShop(Shop shop) {
        deleteById(shop.getShopId());
        log.info("Shop deleted: " + shop.getName());
    }
}
