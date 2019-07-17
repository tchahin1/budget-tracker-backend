package com.example.moneytracker.repositories;

import com.example.moneytracker.models.Income;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IncomeRepository extends CrudRepository<Income, Long> {
    List<Income> findAllByUser_Id(Long id);

    Income findFirstById(Long id);
}
