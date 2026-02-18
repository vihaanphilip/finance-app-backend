package com.vphilip.finance.app.expense.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record ExpenseList(
    @JsonProperty("expenses")
    List<Expense> expenses
) {}

