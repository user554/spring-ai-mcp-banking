package com.example.banking.ai;

import com.example.banking.mcp.McpEventPublisher;
import com.example.banking.model.ContextMessage;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.annotation.Agent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Agent
@RequiredArgsConstructor
@Component
public class EventInterpretationAgent1 {

    private final McpEventPublisher eventPublisher;

    // Example: React to MCP events, analyze and publish new context messages
    @EventListener
    public void onContextMessage(ContextMessage message) {
        if ("balanceUpdate".equals(message.contextId())) {
            String reaction = "AI Agent noticed balance change: " + message.data();
            // Publishing a new MCP event as reaction
            ContextMessage reactionMessage = new ContextMessage(
                    "agentReaction",
                    "EventInterpretationAgent",
                    reaction,
                    LocalDateTime.now()
            );
            eventPublisher.publish(reactionMessage);
        }
    }

    @PostConstruct
    public void init() {
        System.out.println("EventInterpretationAgent is ready");
    }
}
