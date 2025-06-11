package com.example.banking.service;

import com.example.banking.ai.EventInterpretationAgent;
import com.example.banking.model.*;
import com.example.banking.mcp.MCPServerService;
import com.example.banking.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MCPServerService mcpServer;

    @Autowired
    private EventHistoryRepository eventHistoryRepository;

    @Autowired
    private EventInterpretationAgent agent;

    public Account deposit(Long id, double amount) {
        Account account = accountRepository.findById(id).orElseThrow();
        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);

        // Build MCP context message (using tool named get-balance)
        ContextMessage msg = new ContextMessage("getBalance", "accountService", id.toString());
        mcpServer.route(msg);

        // Save event history
        EventHistory event = new EventHistory();
        event.setContextId(msg.contextId());
        event.setSource(msg.source());
        event.setData(msg.data().toString());
        event.setTimestamp(msg.timestamp());
        eventHistoryRepository.save(event);

        // Interpret event via Spring AI agent
        String interpretation = agent.interpretEvent(msg);
        System.out.println("AI Interpretation: " + interpretation);

        return account;
    }
}
