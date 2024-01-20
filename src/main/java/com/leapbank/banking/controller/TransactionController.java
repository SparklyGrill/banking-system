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

        @GetMapping("/transaction/history")
        public ResponseEntity<List<Transaction>> getTransactionHistory(
                @RequestParam Long customerId,
                @RequestParam String startDate,
                @RequestParam String endDate) {
            try {
                // Assuming you have a method in TransactionService to retrieve history
                List<Transaction> transactionHistory = transactionService.getTransactionHistory(customerId, startDate, endDate);

                // Check if history is empty
                if (transactionHistory.isEmpty()) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }

                return new ResponseEntity<>(transactionHistory, HttpStatus.OK);

            } catch (Exception e) {
                // Handle exceptions (e.g., invalid date formats)
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

    }
