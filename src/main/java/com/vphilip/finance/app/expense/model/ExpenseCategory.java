package com.vphilip.finance.app.expense.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("expense_category")
public record ExpenseCategory(
        @Id
        Long id,
        Long expense_type_id,
        String label,
        String description
) {
    public Long getId() {
        return id;
    }

    public Long getExpenseTypeId() {
        return expense_type_id;
    }

    public String getLabel() {
        return label;
    }

    public String getDescription() {
        return description;
    }
}
