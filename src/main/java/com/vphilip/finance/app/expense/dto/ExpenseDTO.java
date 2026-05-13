package com.vphilip.finance.app.expense.dto;

public record ExpenseDTO(
    Long id,
    Long account_id,
    String account_label,
    String description,
    java.math.BigDecimal amount,
    Long expense_type_id,
    String expense_type_label,
    Long expense_category_id,
    String expense_category_label,
    java.util.Date transaction_date,
    java.time.LocalDateTime created_at,
    java.time.LocalDateTime last_modified_at
) {
}
