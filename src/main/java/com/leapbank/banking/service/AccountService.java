package com.leapbank.banking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.leapbank.banking.model.Account;
import com.leapbank.banking.repository.AccountRepository;

import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> getAccountsByCustomerId(Long customerId) {
        return accountRepository.findByCustomerId(customerId);
    }

    public Account getAccountById(Long accountId) {
        return accountRepository.findById(accountId).orElse(null);
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account createAccount(Account account) {
        // Check if the account already exists (e.g., based on account number or other unique identifier)
        if (accountRepository.findByAccountNumber(account.getAccountNumber()).isPresent()) {
            throw new IllegalArgumentException("Account with the same account number already exists");
        }

        // Add any additional validation or business logic before saving the account
        // ...

        // Save the new account
        return accountRepository.save(account);
    }

    public void deleteAccount(Long accountId) {
        // Check if the account exists
        Account existingAccount = accountRepository.findById(accountId).orElse(null);
        if (existingAccount == null) {
            throw new IllegalArgumentException("Account not found");
        }

        // Add any additional validation or business logic before deleting the account
        // ...

        // Delete the account
        accountRepository.deleteById(accountId);
    }

    // Add any other methods as needed
}
