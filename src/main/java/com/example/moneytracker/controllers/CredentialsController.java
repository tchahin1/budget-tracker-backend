package com.example.moneytracker.controllers;

import com.example.moneytracker.repositories.CredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/credentials")
public class CredentialsController {

    @Autowired
    private CredentialsRepository credentialsRepository;
}
