package com.vphilip.finance.app.expense.dto;

public interface ExpenseCategoryDTO {
    Long getId();
    Long getExpense_type_id();
    String getLabel();
    String getDescription();
    String getExpense_type_label();
}