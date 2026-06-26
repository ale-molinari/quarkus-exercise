package it.sfb.backend.testproduct;

import io.quarkus.test.junit.QuarkusTest;
import it.sfb.backend.product.EAvailability;
import it.sfb.backend.product.Product;
import it.sfb.backend.product.ProductRepository;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

@QuarkusTest
public class ProductResourceTest {

    @Inject
    ProductRepository productRepository;

    private static final String PATH = "/product";

    @BeforeAll
    public static void setup(){
        Product product = new Product();
        product.setName("apple");
        product.setPrice(10.0);
        product.setQuantity(10);
        product.setAvailability(EAvailability.AVAILABLE);

        given()
                .body(product).contentType("application/json")
                .when().post(PATH);

        Product product2 = new Product();
        product2.setName("orange");
        product2.setPrice(20.0);
        product2.setQuantity(10);
        product2.setAvailability(EAvailability.AVAILABLE);

        given()
                .body(product2).contentType("application/json")
                .when().post(PATH);

        Product product3 = new Product();
        product3.setName("banana");
        product3.setPrice(15.0);
        product3.setQuantity(10);
        product3.setAvailability(EAvailability.AVAILABLE);

        given()
                .body(product3).contentType("application/json")
                .when().post(PATH);
    }

    @Test
    public void testGetProduct() {

        given()
                .when().get(PATH)
                .then()
                .assertThat()
                .statusCode(200)
                .body(containsString("apple"));
    }

    @Test
    public void testUpdateProduct() {

        Product testProduct = productRepository.findByName("apple");
        UUID id = testProduct.getProductId();
        testProduct.setPrice(11.0);
        given()
                .body(testProduct).contentType("application/json")
                .when().put(PATH + "/{id}", id)
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void testDeleteProduct() {

        Product testProduct = productRepository.findByName("apple");
        UUID id = testProduct.getProductId();
        given()
                .when().delete(PATH + "/{id}", id)
                .then()
                .assertThat()
                .statusCode(200);

    }
}
