package com.example.moneytracker.repositories;

import com.example.moneytracker.models.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
