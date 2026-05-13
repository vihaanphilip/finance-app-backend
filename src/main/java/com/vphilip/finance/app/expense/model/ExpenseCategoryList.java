package com.vphilip.finance.app.expense.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ExpenseCategoryList(
    @JsonProperty ("expense_categories")
    java.util.List<ExpenseCategory> expenseCategories
) {
}
