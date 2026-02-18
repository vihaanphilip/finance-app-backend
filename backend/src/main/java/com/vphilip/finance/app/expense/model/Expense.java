package com.vphilip.finance.app.expense.model;

import org.springframework.data.annotation.Id;

public record Expense(
    @Id
    Long id,
    Long account_id,
    String description,
    java.math.BigDecimal amount,
    Long expense_category_id,
    java.time.LocalDate transaction_date,
    java.time.LocalDateTime created_at,
    java.time.LocalDateTime last_modified_at
) {
    public Long getId() { return id; }
}
