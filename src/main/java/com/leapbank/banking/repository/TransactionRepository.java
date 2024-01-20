package com.leapbank.banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.leapbank.banking.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
