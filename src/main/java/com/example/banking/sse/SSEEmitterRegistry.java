package com.example.banking.sse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.*;

@Component
public class SSEEmitterRegistry {
    private final Map<String, List<SseEmitter>> emittersByContext = new HashMap<>();

    public void register(String contextId, SseEmitter emitter) {
        emittersByContext.computeIfAbsent(contextId, k -> new ArrayList<>()).add(emitter);
        emitter.onCompletion(() -> emittersByContext.get(contextId).remove(emitter));
        emitter.onTimeout(() -> emittersByContext.get(contextId).remove(emitter));
    }

    public List<SseEmitter> getEmitters(String contextId) {
        return emittersByContext.getOrDefault(contextId, Collections.emptyList());
    }
}
