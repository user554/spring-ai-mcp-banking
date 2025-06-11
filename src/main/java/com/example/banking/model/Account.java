package com.example.banking.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Account {
    @Id
    @GeneratedValue
    private Long id;
    private String owner;
    private double balance;
}
