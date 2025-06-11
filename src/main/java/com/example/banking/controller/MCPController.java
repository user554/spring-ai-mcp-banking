package com.example.banking.controller;

import com.example.banking.sse.SSEEmitterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/sse")
public class MCPController {

    @Autowired
    private SSEEmitterRegistry emitterRegistry;

    @GetMapping("/subscribe")
    public SseEmitter subscribe(@RequestParam String contextId) {
        SseEmitter emitter = new SseEmitter(0L); // no timeout
        emitterRegistry.register(contextId, emitter);
        return emitter;
    }
}
