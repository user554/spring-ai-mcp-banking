package com.example.banking.ai;

import com.example.banking.mcp.McpEventPublisher;
import com.example.banking.model.ContextMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.openai.client.ChatClient;
import org.springframework.ai.openai.client.function.FunctionCall;
import org.springframework.ai.openai.client.reactive.ChatCompletionResponse;
import org.springframework.ai.openai.client.reactive.ChatMessage;
import org.springframework.ai.annotation.Agent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@Agent
@RequiredArgsConstructor
@Component
public class EventInterpretationAgent {

    private final McpEventPublisher mcpEventPublisher;
    private final ChatClient chatClient;

    @EventListener
    public void onContextMessage(ContextMessage message) {
        if ("balanceUpdate".equals(message.contextId())) {
            String userPrompt = "Interpret this banking event: " + message.data();

            // Build chat messages for OpenAI
            List<ChatMessage> messages = List.of(
                    ChatMessage.ofUser(userPrompt)
            );

            // Call OpenAI Chat API reactively
            Mono<ChatCompletionResponse> responseMono = chatClient.chatCompletion(messages);

            responseMono.subscribe(response -> {
                String aiReply = response.choices().get(0).message().content();

                ContextMessage reactionMessage = new ContextMessage(
                        "agentReaction",
                        "EventInterpretationAgent",
                        aiReply,
                        LocalDateTime.now()
                );

                mcpEventPublisher.publish(reactionMessage);
            }, error -> {
                System.err.println("OpenAI chat completion failed: " + error.getMessage());
            });
        }
    }
}
