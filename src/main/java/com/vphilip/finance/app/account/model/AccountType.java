package com.vphilip.finance.app.account.model;

import jakarta.persistence.*;

@Entity
@Table(name = "account_type")
public class AccountType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "label")
    private String label;

    @Column(name = "description")
    private String description;

    public AccountType() {}

    public AccountType(Long id, String label, String description) {
        this.id = id;
        this.label = label;
        this.description = description;
    }

    public Long getId() { return id; }
    public String getLabel() { return label; }
    public String getDescription() { return description; }

    public void setId(Long id) { this.id = id; }
    public void setLabel(String label) { this.label = label; }
    public void setDescription(String description) { this.description = description; }
}