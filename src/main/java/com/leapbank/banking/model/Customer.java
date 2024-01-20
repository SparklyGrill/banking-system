package com.leapbank.banking.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;
    private String email;
    private String phoneNumber;

    @OneToMany(mappedBy = "customer")
    private List<Account> accounts;

    // Getters and setters

}
