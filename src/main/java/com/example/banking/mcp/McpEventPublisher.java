package com.example.banking.mcp;

import com.example.banking.model.ContextMessage;
import com.example.banking.model.EventHistory;
import com.example.banking.repository.EventHistoryRepository;
import com.example.banking.sse.SseEmitterRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class McpEventPublisher {

    private final SseEmitterRegistry sseEmitterRegistry;
    private final EventHistoryRepository eventHistoryRepository;

    public void publish(ContextMessage contextMessage) {
        // Send to SSE subscribers
        sseEmitterRegistry.sendEvent(contextMessage.contextId(), contextMessage);

        // Save event history to DB
        EventHistory history = new EventHistory();
        history.setContextId(contextMessage.contextId());
        history.setSource(contextMessage.source());
        history.setData(contextMessage.data().toString());
        history.setTimestamp(contextMessage.timestamp());

        eventHistoryRepository.save(history);
    }
}
