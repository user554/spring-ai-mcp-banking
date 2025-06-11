package com.example.bankingmcp.repository;

import com.example.bankingmcp.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> { }