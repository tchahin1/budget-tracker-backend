package com.example.moneytracker.repositories;

import com.example.moneytracker.models.Reminder;
import org.springframework.data.repository.CrudRepository;

public interface ReminderRepository extends CrudRepository<Reminder, Long> {
    Reminder findByExpense_Id(Long id);
}
