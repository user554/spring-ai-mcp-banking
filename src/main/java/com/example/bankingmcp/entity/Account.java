package com.example.bankingmcp.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String owner;
    private BigDecimal balance;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
}