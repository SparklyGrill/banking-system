package com.leapbank.banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.leapbank.banking.repository.CustomerRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
