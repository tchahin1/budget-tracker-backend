package com.example.moneytracker.controllers;

import com.example.moneytracker.models.Credentials;
import com.example.moneytracker.models.Users;
import com.example.moneytracker.repositories.CredentialsRepository;
import com.example.moneytracker.repositories.UsersRepository;
import dtos.UsersDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private CredentialsRepository credentialsRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UsersController(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity Register(@RequestBody UsersDTO usersDTO)
    {
        Credentials credentials = credentialsRepository.findByUsername(usersDTO.getUsername());
        if(credentials!=null) return new ResponseEntity(HttpStatus.CONFLICT);
        else if(credentials!=null && credentials.getPassword()!= usersDTO.getPassword()) return new ResponseEntity(HttpStatus.BAD_REQUEST);
        else
        {
            Users users = new Users();
            users.setEmail(usersDTO.getEmail());
            users.setLast_name(usersDTO.getLname());
            users.setName(usersDTO.getName());
            usersRepository.save(users);

            credentials = new Credentials();
            credentials.setUsername(usersDTO.getUsername());
            credentials.setPassword(bCryptPasswordEncoder.encode(usersDTO.getPassword()));
            credentials.setUser(users);
            credentialsRepository.save(credentials);
            return new ResponseEntity(credentials, HttpStatus.OK);
        }
    }
}
