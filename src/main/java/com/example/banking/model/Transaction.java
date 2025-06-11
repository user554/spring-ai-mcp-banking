package com.example.banking.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Transaction {
    @Id
    @GeneratedValue
    private Long id;
    private Long accountId;
    private String description;
    private double amount;
    private LocalDateTime timestamp;
}
