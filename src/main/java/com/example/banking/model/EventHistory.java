package com.example.banking.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class EventHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String contextId;
    private String source;

    @Lob
    private String data;

    private LocalDateTime timestamp;
}
