package com.example.moneytracker.controllers;

import com.example.moneytracker.models.Category;
import com.example.moneytracker.models.Credentials;
import com.example.moneytracker.models.Expense;
import com.example.moneytracker.models.Users;
import com.example.moneytracker.repositories.CategoryRepository;
import com.example.moneytracker.repositories.CredentialsRepository;
import com.example.moneytracker.repositories.ExpenseRepository;
import com.example.moneytracker.repositories.UsersRepository;
import dtos.ExpensesAddDTO;
import dtos.ExpensesDeleteDTO;
import dtos.ExpensesEditDTO;
import dtos.ExpensesNoteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private CredentialsRepository credentialsRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/category")
    public ResponseEntity GetExpenseByUserId(@RequestParam Long category, @RequestParam String username)
    {
        Users users = null;
        List<Expense> expense = null;
        Credentials credentials = credentialsRepository.findByUsername(username);
        if(credentials != null) users = credentials.getUser();
        if(users != null) expense = expenseRepository.findByCategory_IdAndUser_Id(category, users.getId());
        if(expense!=null && expense.size()!=0) return new ResponseEntity(expense, HttpStatus.OK);
        else return new ResponseEntity("{\"ID\": \"-1\"}",HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/delete")
    public ResponseEntity DeleteExpenseById(@RequestBody ExpensesDeleteDTO expensesDeleteDTO){
        Expense expense = expenseRepository.findFirstById(expensesDeleteDTO.getId());
        if(expense != null){
            expense.setCategory(null);
            expense.setUser(null);
            expenseRepository.save(expense);
            expenseRepository.delete(expense);
            return new ResponseEntity("{\"ID\": \"-1\"}", HttpStatus.OK);
        }
        else return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/add")
    public ResponseEntity AddExpense(@RequestBody ExpensesAddDTO expensesAddDTO){
        if(expensesAddDTO.getCategoryId()==null || expensesAddDTO.getUserId()==null || expensesAddDTO.getName()==null || expensesAddDTO.getAmmount()==null)
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        else {
            Expense expense = new Expense();
            Credentials credentials = credentialsRepository.findByUsername(expensesAddDTO.getUserId());
            Users users = credentials.getUser();
            expense.setUser(users);
            Optional<Category> optionalCategory = categoryRepository.findById(expensesAddDTO.getCategoryId());
            Category category = optionalCategory.get();
            expense.setCategory(category);
            expense.setAmmount(expensesAddDTO.getAmmount());
            expense.setName(expensesAddDTO.getName());
            expenseRepository.save(expense);
            return new ResponseEntity(expense, HttpStatus.OK);
        }
    }

    @PostMapping("/edit")
    public ResponseEntity EditExpense(@RequestBody ExpensesEditDTO expensesEditDTO){
        Optional<Expense> optionalExpense = expenseRepository.findById(expensesEditDTO.getId());
        Expense expense = optionalExpense.get();
        if(expense!=null){
            if(expense.getAmmount()!=expensesEditDTO.getAmmount()) expense.setAmmount(expensesEditDTO.getAmmount());
            if(expense.getName()!=expensesEditDTO.getName()) expense.setName(expensesEditDTO.getName());
            expenseRepository.save(expense);
            return new ResponseEntity(expense, HttpStatus.OK);
        }
        else return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/note")
    public ResponseEntity NoteExpense(@RequestBody ExpensesNoteDTO expenseNoteDTO){
        Optional<Expense> optionalExpense = null;
        if(expenseNoteDTO.getId()!=null) optionalExpense = expenseRepository.findById(expenseNoteDTO.getId());
        Expense expense = optionalExpense.get();
        if(expense!=null){
            expense.setNote(expenseNoteDTO.getNote());
            expenseRepository.save(expense);
            return new ResponseEntity(expense, HttpStatus.OK);
        }
        else return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/notes")
    public ResponseEntity GetNotes(@RequestParam Long id){
        Optional<Expense> optionalExpense = expenseRepository.findById(id);
        Expense expense = optionalExpense.get();
        if(expense!=null && expense.getNote()!=null && !expense.getNote().equals("")){
            return new ResponseEntity(expense, HttpStatus.OK);
        }
        else return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
