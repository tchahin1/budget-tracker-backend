package com.example.moneytracker.controllers;

import com.example.moneytracker.models.Credentials;
import com.example.moneytracker.models.Income;
import com.example.moneytracker.models.Users;
import com.example.moneytracker.repositories.CredentialsRepository;
import com.example.moneytracker.repositories.IncomeRepository;
import dtos.IncomesAddDTO;
import dtos.IncomesDeleteDTO;
import dtos.IncomesEditDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/incomes")
public class IncomeController {

    @Autowired
    private IncomeRepository incomeRepository;

    @Autowired
    private CredentialsRepository credentialsRepository;

    @GetMapping("/get")
    public ResponseEntity GetIncomeByUserId(@RequestParam String username)
    {
        Users users = null;
        List<Income> incomes = null;
        Credentials credentials = credentialsRepository.findByUsername(username);
        if(credentials != null) users = credentials.getUser();
        if(users != null) incomes = incomeRepository.findAllByUser_Id(users.getId());
        if(incomes!=null && incomes.size()!=0) return new ResponseEntity(incomes, HttpStatus.OK);
        else return new ResponseEntity("{\"ID\": \"-1\"}",HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/delete")
    public ResponseEntity DeleteIncomeById(@RequestBody IncomesDeleteDTO incomesDeleteDTO){
        Income income = incomeRepository.findFirstById(incomesDeleteDTO.getId());
        if(income != null){
            income.setUser(null);
            incomeRepository.save(income);
            incomeRepository.delete(income);
            return new ResponseEntity("{\"ID\": \"-1\"}", HttpStatus.OK);
        }
        else return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/add")
    public ResponseEntity AddIncome(@RequestBody IncomesAddDTO incomesAddDTO){
        if(incomesAddDTO.getUserId()==null || incomesAddDTO.getName()==null || incomesAddDTO.getAmmount()==null)
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        else {
            Income income = new Income();
            Credentials credentials = credentialsRepository.findByUsername(incomesAddDTO.getUserId());
            Users users = credentials.getUser();
            income.setUser(users);
            income.setAmmount(incomesAddDTO.getAmmount());
            income.setName(incomesAddDTO.getName());
            incomeRepository.save(income);
            return new ResponseEntity(income, HttpStatus.OK);
        }
    }

    @PostMapping("/edit")
    public ResponseEntity EditIncome(@RequestBody IncomesEditDTO incomesEditDTO){
        Optional<Income> optionalIncome = incomeRepository.findById(incomesEditDTO.getId());
        Income income = optionalIncome.get();
        if(income!=null){
            if(income.getAmmount()!=incomesEditDTO.getAmmount()) income.setAmmount(incomesEditDTO.getAmmount());
            if(income.getName()!=incomesEditDTO.getName()) income.setName(incomesEditDTO.getName());
            incomeRepository.save(income);
            return new ResponseEntity(income, HttpStatus.OK);
        }
        else return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
