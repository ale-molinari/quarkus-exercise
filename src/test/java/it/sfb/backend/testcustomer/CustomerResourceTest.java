package it.sfb.backend.testcustomer;

import io.quarkus.test.junit.QuarkusTest;
import it.sfb.backend.customer.Customer;
import it.sfb.backend.customer.CustomerRepository;
import it.sfb.backend.customer.EStatus;
import it.sfb.backend.product.Product;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

@QuarkusTest
public class CustomerResourceTest {

    @Inject
    CustomerRepository customerRepository;

    private static final String PATH = "/customer";

    @BeforeAll
    public static void setup() {
        Customer customer = new Customer();
        customer.setBirthDate(LocalDate.now());
        customer.setName("testCustomer");
        customer.setEmail("testmail@gmail.com");
        customer.setStatus(EStatus.ACTIVE);

        given()
                .body(customer).contentType("application/json")
                .when().post(PATH);
    }

    @Test
    public void testGetCustomers() {
        given()
                .when().get(PATH)
                .then()
                .assertThat()
                .statusCode(200)
                .body(containsString("testcustomer"), containsString("testmail@gmail.com"));
    }

    @Test
    public void testGetCustomerByIdAndName() {
        Customer testCustomer = customerRepository.findByName("testcustomer");
        UUID id = testCustomer.getCustomerId();

        given()
                .when().get(PATH + "/{id}", id)
                .then()
                .assertThat()
                .body(containsString("testcustomer"), containsString("testmail@gmail.com"));

        given()
                .when().get(PATH + "/search/{name}", "testcustomer")
                .then()
                .assertThat()
                .body(containsString("testmail@gmail.com"));
    }

    @Test
    public void testUpdateNewCustomer() {
        Customer testCustomer = customerRepository.findByName("testcustomer");
        UUID id = testCustomer.getCustomerId();
        testCustomer.setCustomerId(id);
        testCustomer.setStatus(EStatus.INACTIVE);
        testCustomer.setProducts(List.of(new Product()));

        given()
                .body(testCustomer).contentType("application/json")
                .when().put(PATH + "/{id}", id)
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void testDeleteNewCustomer() {
        Customer testCustomer = customerRepository.findByName("testcustomer");
        UUID id = testCustomer.getCustomerId();

        given()
                .when().delete(PATH + "/{id}", id)
                .then()
                .assertThat()
                .statusCode(200);
    }
}

