package com.leapbank.banking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.leapbank.banking.model.Customer;
import com.leapbank.banking.repository.CustomerRepository;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer getCustomerById(Long customerId) {
        return customerRepository.findById(customerId).orElse(null);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer createCustomer(Customer customer) {
        // Check if the customer already exists (e.g., based on email or other unique identifier)
        if (customerRepository.findByEmail(customer.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Customer with the same email already exists");
        }
        return customerRepository.save(customer);
    }

    public void deleteCustomer(Long customerId) {
        // Check if the customer exists
        Customer existingCustomer = customerRepository.findById(customerId).orElse(null);
        if (existingCustomer == null) {
            throw new IllegalArgumentException("Customer not found");
        }
        customerRepository.deleteById(customerId);
    }

    // Add any other methods as needed
}
