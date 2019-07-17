package com.example.moneytracker.repositories;

import com.example.moneytracker.models.Expense;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends CrudRepository<Expense, Long> {
    List<Expense> findByCategory_IdAndUser_Id(Long categoryId, Long userId);

    Expense findFirstById(Long id);
}
