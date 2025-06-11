package com.example.bankingmcp.controller;

import com.example.bankingmcp.entity.Account;
import com.example.bankingmcp.repository.AccountRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
public class SseController {

    private final AccountRepository accountRepository;

    public SseController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @GetMapping(value = "/sse/accounts", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamAccountBalances() {
        return Flux.interval(Duration.ofSeconds(2))
                .map(seq -> {
                    StringBuilder sb = new StringBuilder("Live Account Balances:\n");
                    for (Account acc : accountRepository.findAll()) {
                        sb.append(acc.getOwner())
                          .append(": $")
                          .append(acc.getBalance())
                          .append("\n");
                    }
                    return sb.toString();
                });
    }
}