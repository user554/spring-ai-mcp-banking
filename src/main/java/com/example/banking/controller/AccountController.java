package com.example.banking.controller;

import com.example.banking.model.Account;
import com.example.banking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/{id}/deposit")
    public Account deposit(@PathVariable Long id, @RequestParam double amount) {
        return accountService.deposit(id, amount);
    }
}
