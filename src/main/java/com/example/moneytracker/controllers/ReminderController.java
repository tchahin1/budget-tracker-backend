package com.example.moneytracker.controllers;

import com.example.moneytracker.models.Expense;
import com.example.moneytracker.models.Reminder;
import com.example.moneytracker.repositories.ExpenseRepository;
import com.example.moneytracker.repositories.ReminderRepository;
import dtos.RemindersAddDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/reminders")
public class ReminderController {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private ReminderRepository reminderRepository;

    @PostMapping("/add")
    public ResponseEntity AddExpense(@RequestBody RemindersAddDTO remindersAddDTO){
        String time = remindersAddDTO.getTime();
        String date = remindersAddDTO.getDate();
        if(time.charAt(0)!='0' && time.charAt(1)==':') time='0'+time;
        String datetime = date + " " + time;
        LocalDateTime localDateTime = LocalDateTime.parse(datetime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        Optional<Expense> optionalExpense = null;
        if(remindersAddDTO.getExpenseId()!=null) optionalExpense = expenseRepository.findById(remindersAddDTO.getExpenseId());
        Expense expense = optionalExpense.get();
        if(expense!=null) {
            Reminder existingReminder = reminderRepository.findByExpense_Id(expense.getId());
            Reminder reminder = null;
            if(existingReminder==null) {
                reminder = new Reminder();
                reminder.setExpense(expense);
                reminder.setDateTime(localDateTime);
                reminder.setMessage(remindersAddDTO.getText());
                reminderRepository.save(reminder);
            }
            else{
                reminder = existingReminder;
                reminder.setDateTime(localDateTime);
                reminder.setMessage(remindersAddDTO.getText());
                reminderRepository.save(reminder);
            }
            return new ResponseEntity(reminder, HttpStatus.OK);
        }
        else return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/get")
    public ResponseEntity GetReminders(){
        LocalDateTime now = LocalDateTime.now();
        String dateTimeNow = now.toString();
        String[] dateTime = dateTimeNow.split("T");
        String date = dateTime[0];
        String time = dateTime[1];
        String hours = time.split(":")[0];
        String minutes = time.split(":")[1];
        time = hours + ":" + minutes;
        dateTimeNow = date + " " + time;
        now = LocalDateTime.parse(dateTimeNow, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        LocalDateTime lowerBound = now.minusMinutes(5);
        List<Reminder> reminders = (List<Reminder>) reminderRepository.findAll();
        List<Reminder> results = new ArrayList<Reminder>();
        for(int i=0; i<reminders.size(); i++){
            Reminder reminder = reminders.get(i);
            if(reminder.getDateTime().isAfter(lowerBound) && reminder.getDateTime().isBefore(now))
                results.add(reminder);
        }
        return new ResponseEntity(results, HttpStatus.OK);
    }
}
