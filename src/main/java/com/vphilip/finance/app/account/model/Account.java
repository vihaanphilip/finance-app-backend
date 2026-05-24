package com.vphilip.finance.app.account.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "account_type_id")
    private Long account_type_id;

    @Column(name = "starting_amount")
    private BigDecimal starting_amount;

    public Account() {}

    public Account(Long id, String name, String description, Long account_type_id, BigDecimal starting_amount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.account_type_id = account_type_id;
        this.starting_amount = starting_amount;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public Long getAccount_type_id() { return account_type_id; }
    public BigDecimal getStarting_amount() { return starting_amount; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setAccount_type_id(Long account_type_id) { this.account_type_id = account_type_id; }
    public void setStarting_amount(BigDecimal starting_amount) { this.starting_amount = starting_amount; }
}