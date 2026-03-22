package com.vphilip.finance.app.account.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("account_type")
public record AccountType (
    @Id
    Long id,
    String label,
    String description
) {
    public Long getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public String getDescription() {
        return description;
    }
}
