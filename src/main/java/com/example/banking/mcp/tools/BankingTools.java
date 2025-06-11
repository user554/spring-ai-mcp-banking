package com.example.banking.mcp.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.banking.repository.AccountRepository;
import com.example.banking.repository.TransactionRepository;

@Component
public class BankingTools {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Tool(description = "Get the balance of an account by ID")
    public String getBalance(String accountId) {
        return accountRepository.findById(Long.parseLong(accountId))
                .map(acc -> "Balance for account " + accountId + " is $" + acc.getBalance())
                .orElse("Account not found");
    }

    @Tool(description = "Get transaction statements for an account")
    public String getStatements(String accountId) {
        var txns = transactionRepository.findByAccountId(Long.parseLong(accountId));
        if (txns.isEmpty()) return "No transactions found";
        StringBuilder sb = new StringBuilder();
        txns.forEach(txn -> sb.append(txn.getTimestamp())
                              .append(": ")
                              .append(txn.getDescription())
                              .append(" - $")
                              .append(txn.getAmount())
                              .append("\n"));
        return sb.toString();
    }
}
