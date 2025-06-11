package com.example.banking.mcp;

import com.example.banking.model.ContextMessage;
import com.example.banking.sse.SSEEmitterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;

@Service
public class MCPServerService {

    @Autowired
    private SSEEmitterRegistry emitterRegistry;

    public void route(ContextMessage message) {
        List<SseEmitter> emitters = emitterRegistry.getEmitters(message.contextId());
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(SseEmitter.event()
                        .name("mcp-event")
                        .data(message));
            } catch (IOException e) {
                emitter.completeWithError(e);
            }
        }
    }
}
