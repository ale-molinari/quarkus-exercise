package it.sfb.backend.testshop;

import io.quarkus.test.junit.QuarkusTest;
import it.sfb.backend.shop.Shop;
import it.sfb.backend.shop.ShopRepository;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

@QuarkusTest
public class ShopResourceTest {

    @Inject
    ShopRepository shopRepository;

    private static final String PATH = "/shop";

    @BeforeAll
    public static void setup(){
        Shop shop = new Shop();
        shop.setName("Apple");
        shop.setCity("Tokyo");

        given()
                .body(shop).contentType("application/json")
                .when().post(PATH);
    }

    @Test
    public void testGetShops(){

        given()
                .when().get(PATH)
                .then()
                .assertThat()
                .statusCode(200)
                .body(containsString("apple"), containsString("Tokyo"));
    }

    @Test
    public void testUpdateProduct() {

        Shop testShop = shopRepository.findByName("apple");
        UUID id = testShop.getShopId();
        testShop.setCity("Rome");

        given()
                .body(testShop).contentType("application/json")
                .when().put(PATH + "/{id}", id)
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void testDeleteShop(){

        Shop testShop = shopRepository.findByName("apple");
        UUID id = testShop.getShopId();

        given()
                .when().delete(PATH + "/{id}", id)
                .then()
                .assertThat()
                .statusCode(204);
    }
}
