package com.crudapi.crud.repository;

import com.crudapi.crud.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByEmail(String email);
    Optional<Client> findByPhone(String phone);

    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
}
