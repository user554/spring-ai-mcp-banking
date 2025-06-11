package com.example.bankingmcp.service;

import com.example.bankingmcp.entity.Account;
import com.example.bankingmcp.repository.AccountRepository;
import org.springframework.ai.tool.Tool;
import org.springframework.ai.tool.ToolParam;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BankTools {
    private final AccountRepository accountRepository;

    public BankTools(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Tool(description = "Get account balance")
    public BigDecimal getBalance(@ToolParam(description = "Account ID") Long id) {
        return accountRepository.findById(id)
                .map(Account::getBalance)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
    }

    @Tool(description = "Transfer money between accounts")
    public String transfer(@ToolParam Long fromId, @ToolParam Long toId, @ToolParam BigDecimal amount) {
        Account from = accountRepository.findById(fromId).orElseThrow();
        Account to = accountRepository.findById(toId).orElseThrow();
        if (from.getBalance().compareTo(amount) < 0) {
            return "Insufficient funds";
        }
        from.setBalance(from.getBalance().subtract(amount));
        to.setBalance(to.getBalance().add(amount));
        accountRepository.saveAll(List.of(from, to));
        return "Transfer successful";
    }
}