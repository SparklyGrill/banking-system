package com.leapbank.banking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.leapbank.banking.model.Transaction;
import com.leapbank.banking.service.TransactionService;

import java.util.List;
import org.springframework.http.ResponseEntity;

    // Implement REST endpoints for transactions

    @RestController
    @RequestMapping("/transaction")
    public class TransactionController {

        private final TransactionService transactionService;

        @Autowired
        public TransactionController(TransactionService transactionService) {
            this.transactionService = transactionService;
        }

        @GetMapping("/customer/{id}")
        public ResponseEntity<Customer> getCustomerDetails(@PathVariable Long id) {
            Customer customer = transactionService.getCustomerDetails(id);
            return customer != null ? ResponseEntity.ok(customer) : ResponseEntity.notFound().build();
        }

        @PostMapping
        public ResponseEntity<Long> processPayment(@RequestBody Transaction transaction) {
            Long transactionId = transactionService.processPayment(transaction);
            return ResponseEntity.ok(transactionId);
        }

        @GetMapping("/history/{customerId}")
        public ResponseEntity<List<Transaction>> getTransactionHistory(
                @PathVariable Long customerId,
                @RequestParam(required = false) String filterName,
                @RequestParam(required = false) String filterValue
        ) {
            List<Transaction> transactionHistory = transactionService.getTransactionHistory(customerId, filterName, filterValue);
            return ResponseEntity.ok(transactionHistory);
        }
    }
