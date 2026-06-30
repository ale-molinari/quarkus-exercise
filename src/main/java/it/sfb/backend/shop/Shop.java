package it.sfb.backend.shop;

import it.sfb.backend.product.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "shops")
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "shop_id")
    private UUID shopId;

    @NotNull
    private String name;

    private String city;

    @OneToMany(mappedBy = "shop", fetch = FetchType.EAGER)
    List<Product> products;

    public void setShopId(UUID shopId) {
        this.shopId = shopId;
    }

    public UUID getShopId() {
        return shopId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<Product> getProducts() {
        return products;
    }
}
