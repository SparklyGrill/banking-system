package com.leapbank.banking.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_account_id")
    private Account senderAccount;

    @ManyToOne
    @JoinColumn(name = "receiver_account_id")
    private Account receiverAccount;

    private double amount;

    private String currencyId;

    private String message;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    // Getters and setters

}
