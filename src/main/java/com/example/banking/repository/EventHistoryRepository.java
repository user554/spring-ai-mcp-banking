package com.example.banking.repository;

import com.example.banking.model.EventHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventHistoryRepository extends JpaRepository<EventHistory, Long> {}
