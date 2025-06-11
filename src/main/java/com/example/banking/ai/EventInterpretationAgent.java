package com.example.banking.ai;

import com.example.banking.model.ContextMessage;
import org.springframework.ai.chat.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventInterpretationAgent {

    @Autowired
    private ChatClient chatClient;

    public String interpretEvent(ContextMessage message) {
        // Compose prompt to invoke tool by name and input
        String prompt = """
            You are a helpful banking assistant.
            Use tool "%s" with input "%s" and return the concise result.
            """.formatted(message.contextId(), message.data().toString());

        return chatClient.chat(prompt);
    }
}
