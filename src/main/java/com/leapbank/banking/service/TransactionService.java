package com.leapbank.banking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.leapbank.banking.model.Transaction;
import com.leapbank.banking.repository.TransactionRepository;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, CustomerRepository customerRepository) {
        this.transactionRepository = transactionRepository;
        this.customerRepository = customerRepository;
    }

    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id).orElse(null);
    }

    @Transactional
    public Long createTransaction(Transaction transaction) {
        // Validate sender and receiver accounts
        Account senderAccount = transaction.getSenderAccount();
        Account receiverAccount = transaction.getReceiverAccount();

        if (senderAccount == null || receiverAccount == null) {
            throw new IllegalArgumentException("Sender and receiver accounts must be specified");
        }

        // Validate sufficient funds in the sender's account
        double amount = transaction.getAmount();
        if (senderAccount.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient funds in the sender's account");
        }

        // Validate sender and receiver are different accounts
        if (senderAccount.equals(receiverAccount)) {
            throw new IllegalArgumentException("Sender and receiver accounts must be different");
        }

        // Validate that both sender and receiver belong to the same customer
        if (!senderAccount.getCustomer().equals(receiverAccount.getCustomer())) {
            throw new IllegalArgumentException("Sender and receiver must belong to the same customer");
        }

        // Update sender's account balance
        senderAccount.setBalance(senderAccount.getBalance() - amount);
        customerRepository.save(senderAccount.getCustomer());

        // Update receiver's account balance
        receiverAccount.setBalance(receiverAccount.getBalance() + amount);
        customerRepository.save(receiverAccount.getCustomer());

        // Save the transaction
        Transaction savedTransaction = transactionRepository.save(transaction);
        return savedTransaction.getId();
    }

    public List<Transaction> getTransactionHistoryByCustomerId(Long customerId, String filterName, String filterValue) {
        // Check if the customer exists
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer == null) {
            // Handle the case where the customer doesn't exist
            return Collections.emptyList();
        }

        // Retrieve transaction history for the customer
        List<Transaction> transactionHistory = new ArrayList<>();
        for (Account account : customer.getAccounts()) {
            transactionHistory.addAll(account.getSentTransactions());
            transactionHistory.addAll(account.getReceivedTransactions());
        }

        // Apply optional filters if provided
        if (filterName != null && filterValue != null) {
            switch (filterName.toLowerCase()) {
                case "amount":
                    // Filter by transaction amount
                    transactionHistory = transactionHistory.stream()
                            .filter(transaction -> Double.toString(transaction.getAmount()).equals(filterValue))
                            .collect(Collectors.toList());
                    break;
                case "currency":
                    // Filter by currency
                    transactionHistory = transactionHistory.stream()
                            .filter(transaction -> transaction.getCurrencyId().equals(filterValue))
                            .collect(Collectors.toList());
                    break;
                // Add more cases for additional filters as needed
                default:
                    // Handle unknown filter names
                    throw new IllegalArgumentException("Unknown filter name: " + filterName);
            }
        }

        return transactionHistory;
    }

}

