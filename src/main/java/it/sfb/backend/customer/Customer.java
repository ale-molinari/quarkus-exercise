package it.sfb.backend.customer;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import it.sfb.backend.product.Product;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "customers")
public class Customer extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "customer_id")
    private UUID customerId;

    private String name;

    @Column(unique = true)
    private String email;

    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private EStatus status;

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    public List<Product> products;

    public UUID getCustomerId(){
        return this.customerId;
    }

    public void setCustomerId(UUID customerId){
        this.customerId = customerId;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getEmail(){
        return this.email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public LocalDate getBirthDate(){
        return this.birthDate;
    }

    public void setBirthDate(LocalDate birthDate){
        this.birthDate = birthDate;
    }

    public EStatus getStatus(){
        return this.status;
    }

    public void setStatus(EStatus status){
        this.status = status;
    }

    public List<Product> getProducts(){
        return this.products;
    }

    public void setProducts(List<Product> products){
        this.products = products;
    }
}
