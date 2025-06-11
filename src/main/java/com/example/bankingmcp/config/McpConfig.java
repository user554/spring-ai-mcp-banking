package com.example.bankingmcp.config;

import com.example.bankingmcp.service.BankTools;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.MethodToolCallbackProvider;

import java.util.List;

@Configuration
public class McpConfig {
    @Bean
    public ToolCallbackProvider toolCallbackProvider(BankTools bankTools) {
        return new MethodToolCallbackProvider(List.of(bankTools));
    }
}