package com.vphilip.finance.app.expense.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public interface ExpenseDTO {
    Long getId();
    Long getAccount_id();
    String getAccount_label();
    String getDescription();
    BigDecimal getAmount();
    Long getExpense_type_id();
    String getExpense_type_label();
    Long getExpense_category_id();
    String getExpense_category_label();
    LocalDate getTransaction_date();
    LocalDateTime getCreated_at();
    LocalDateTime getLast_modified_at();
}