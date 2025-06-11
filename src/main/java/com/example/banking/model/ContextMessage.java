package com.example.banking.model;

import java.time.LocalDateTime;

public record ContextMessage(String contextId, String source, Object data, LocalDateTime timestamp) {
public ContextMessage(String contextId, String source, Object data) {
        this(contextId, source, data, LocalDateTime.now());
        }
        }
