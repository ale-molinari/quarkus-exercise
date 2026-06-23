package it.sfb.backend.customer;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "customers")
public class Customer extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private String email;

    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private EStatus status;

//    @OneToMany(mappedBy = "customer")
//    public List<Product> products;

    public UUID getId(){
        return this.id;
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
}
