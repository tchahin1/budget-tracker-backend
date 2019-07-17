package com.example.moneytracker.repositories;

import com.example.moneytracker.models.Credentials;
import org.springframework.data.repository.CrudRepository;

public interface CredentialsRepository extends CrudRepository<Credentials, Long> {
    Credentials findByUsername(String username);
}
