package com.example.banking.sse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SseEmitterRegistry {

    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    public void register(String contextId, SseEmitter emitter) {
        emitters.put(contextId, emitter);

        emitter.onCompletion(() -> emitters.remove(contextId));
        emitter.onTimeout(() -> emitters.remove(contextId));
        emitter.onError(e -> emitters.remove(contextId));
    }

    public void sendEvent(String contextId, Object event) {
        SseEmitter emitter = emitters.get(contextId);
        if (emitter != null) {
            try {
                emitter.send(event);
            } catch (IOException e) {
                emitters.remove(contextId);
            }
        }
    }

    public List<SseEmitter> getEmitters(String contextId) {
        return emitters.getOrDefault(contextId, Collections.emptyList());
    }
}
