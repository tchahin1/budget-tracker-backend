package com.example.moneytracker.repositories;

import com.example.moneytracker.models.Users;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<Users, Long> {
}
