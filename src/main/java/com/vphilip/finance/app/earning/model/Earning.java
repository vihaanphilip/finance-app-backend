package com.vphilip.finance.app.earning.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "earning")
public class Earning {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_id")
    private Long account_id;

    @Column(name = "description")
    private String description;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "earning_type_id")
    private Long earning_type_id;

    @Column(name = "earning_category_id")
    private Long earning_category_id;

    @Column(name = "transaction_date")
    private LocalDate transaction_date;

    @Column(name = "created_at")
    private LocalDateTime created_at;

    @Column(name = "last_modified_at")
    private LocalDateTime last_modified_at;

    public Earning() {}

    public Earning(Long id, Long account_id, String description, BigDecimal amount,
                   Long earning_type_id, Long earning_category_id,
                   LocalDate transaction_date, LocalDateTime created_at, LocalDateTime last_modified_at) {
        this.id = id;
        this.account_id = account_id;
        this.description = description;
        this.amount = amount;
        this.earning_type_id = earning_type_id;
        this.earning_category_id = earning_category_id;
        this.transaction_date = transaction_date;
        this.created_at = created_at;
        this.last_modified_at = last_modified_at;
    }

    public Long getId() { return id; }
    public Long getAccount_id() { return account_id; }
    public String getDescription() { return description; }
    public BigDecimal getAmount() { return amount; }
    public Long getEarning_type_id() { return earning_type_id; }
    public Long getEarning_category_id() { return earning_category_id; }
    public LocalDate getTransaction_date() { return transaction_date; }
    public LocalDateTime getCreated_at() { return created_at; }
    public LocalDateTime getLast_modified_at() { return last_modified_at; }

    public void setId(Long id) { this.id = id; }
    public void setAccount_id(Long account_id) { this.account_id = account_id; }
    public void setDescription(String description) { this.description = description; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public void setEarning_type_id(Long earning_type_id) { this.earning_type_id = earning_type_id; }
    public void setEarning_category_id(Long earning_category_id) { this.earning_category_id = earning_category_id; }
    public void setTransaction_date(LocalDate transaction_date) { this.transaction_date = transaction_date; }
    public void setCreated_at(LocalDateTime created_at) { this.created_at = created_at; }
    public void setLast_modified_at(LocalDateTime last_modified_at) { this.last_modified_at = last_modified_at; }
}