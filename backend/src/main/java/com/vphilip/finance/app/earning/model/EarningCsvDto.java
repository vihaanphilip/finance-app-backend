package com.vphilip.finance.app.earning.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class EarningCsvDto {
    @CsvBindByName
    private Long id;

    @CsvBindByName(column = "account_id")
    private Long account_id;

    @CsvBindByName
    private String description;

    @CsvBindByName
    private BigDecimal amount;

    @CsvBindByName(column = "earning_type_id")
    private Long earning_type_id;

    @CsvBindByName(column = "earning_category_id")
    private Long earning_category_id;

    @CsvBindByName(column = "created_at")
    @CsvDate(value = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime created_at;

    @CsvBindByName(column = "last_modified_at")
    @CsvDate(value = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime last_modified_at;

    // Default constructor
    public EarningCsvDto() {}

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getAccount_id() { return account_id; }
    public void setAccount_id(Long account_id) { this.account_id = account_id; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public Long getEarning_type_id() { return earning_type_id; }
    public void setEarning_type_id(Long earning_type_id) { this.earning_type_id = earning_type_id; }

    public Long getEarning_category_id() { return earning_category_id; }
    public void setEarning_category_id(Long earning_category_id) { this.earning_category_id = earning_category_id; }

    public LocalDateTime getCreated_at() { return created_at; }
    public void setCreated_at(LocalDateTime created_at) { this.created_at = created_at; }

    public LocalDateTime getLast_modified_at() { return last_modified_at; }
    public void setLast_modified_at(LocalDateTime last_modified_at) { this.last_modified_at = last_modified_at; }

    // Convert to Earning
    public Earning toEarning() {
        return new Earning(
            id,
            account_id,
            description,
            amount,
            earning_type_id,
            earning_category_id,
            created_at,
            last_modified_at
        );
    }
}
