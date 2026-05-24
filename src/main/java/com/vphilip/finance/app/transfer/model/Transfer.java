package com.vphilip.finance.app.transfer.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "transfer")
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "from_account_id")
    private Long from_account_id;

    @Column(name = "to_account_id")
    private Long to_account_id;

    @Column(name = "description")
    private String description;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "transfer_category_id")
    private Long transfer_category_id;

    @Column(name = "transaction_date")
    private LocalDate transaction_date;

    @Column(name = "created_at")
    private LocalDateTime created_at;

    @Column(name = "last_modified_at")
    private LocalDateTime last_modified_at;

    public Transfer() {}

    public Transfer(Long id, Long from_account_id, Long to_account_id, String description,
                    BigDecimal amount, Long transfer_category_id, LocalDate transaction_date,
                    LocalDateTime created_at, LocalDateTime last_modified_at) {
        this.id = id;
        this.from_account_id = from_account_id;
        this.to_account_id = to_account_id;
        this.description = description;
        this.amount = amount;
        this.transfer_category_id = transfer_category_id;
        this.transaction_date = transaction_date;
        this.created_at = created_at;
        this.last_modified_at = last_modified_at;
    }

    public Long getId() { return id; }
    public Long getFrom_account_id() { return from_account_id; }
    public Long getTo_account_id() { return to_account_id; }
    public String getDescription() { return description; }
    public BigDecimal getAmount() { return amount; }
    public Long getTransfer_category_id() { return transfer_category_id; }
    public LocalDate getTransaction_date() { return transaction_date; }
    public LocalDateTime getCreated_at() { return created_at; }
    public LocalDateTime getLast_modified_at() { return last_modified_at; }

    public void setId(Long id) { this.id = id; }
    public void setFrom_account_id(Long from_account_id) { this.from_account_id = from_account_id; }
    public void setTo_account_id(Long to_account_id) { this.to_account_id = to_account_id; }
    public void setDescription(String description) { this.description = description; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public void setTransfer_category_id(Long transfer_category_id) { this.transfer_category_id = transfer_category_id; }
    public void setTransaction_date(LocalDate transaction_date) { this.transaction_date = transaction_date; }
    public void setCreated_at(LocalDateTime created_at) { this.created_at = created_at; }
    public void setLast_modified_at(LocalDateTime last_modified_at) { this.last_modified_at = last_modified_at; }
}