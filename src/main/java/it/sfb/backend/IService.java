package it.sfb.backend;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import java.util.Optional;

public interface IService<T, Id> extends PanacheRepositoryBase<T, Id> {

    default T findByName(String name) {
        return findByNameOptional(name).orElseThrow(
                () -> new IllegalArgumentException("Shop not found with name: " + name)
        );
    }

    default T findByIdOrThrow(Id id) {
        return findByIdOptional(id).orElseThrow(
                () -> new IllegalArgumentException("Customer not found with id: " + id)
        );
    }

    default Optional<T> findByNameOptional(String name) {
        return find("name", name).firstResultOptional();
    }

}
