package com.vphilip.finance.app.expense.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ExpenseTypeList(
    @JsonProperty("expense_types")
    java.util.List<ExpenseType> expenseTypes
) {
}

