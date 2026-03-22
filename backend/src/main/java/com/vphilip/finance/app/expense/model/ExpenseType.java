package com.vphilip.finance.app.expense.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("expense_type")
public record ExpenseType(
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

