package com.leapbank.banking.controller;

import com.leapbank.banking.model.Account;
import com.leapbank.banking.model.Customer;
import com.leapbank.banking.model.Transaction;
import com.leapbank.banking.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // New methods
    @GetMapping("/transaction/sender/{id}")
    public ResponseEntity<Account> getSenderAccount(@PathVariable Long id) {
        Transaction transaction = transactionService.getTransactionById(id);
        Account senderAccount = transactionService.getSenderAccount(transaction);
        return senderAccount != null ? ResponseEntity.ok(senderAccount) : ResponseEntity.notFound().build();
    }

    @GetMapping("/transaction/receiver/{id}")
    public ResponseEntity<Account> getReceiverAccount(@PathVariable Long id) {
        Transaction transaction = transactionService.getTransactionById(id);
        Account receiverAccount = transactionService.getReceiverAccount(transaction);
        return receiverAccount != null ? ResponseEntity.ok(receiverAccount) : ResponseEntity.notFound().build();
    }

    // Add more methods as needed
}
