package com.vphilip.finance.app.expense.dto;

public record ExpenseCategoryDTO(
        Long id,
        Long expense_type_id,
        String label,
        String description,
        String expense_type_label
) {}