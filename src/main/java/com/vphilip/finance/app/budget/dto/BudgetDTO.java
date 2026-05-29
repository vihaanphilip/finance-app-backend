package com.vphilip.finance.app.budget.dto;

import java.math.BigDecimal;

public interface BudgetDTO {
    Long getId();
    String getName();
    String getDescription();
    BigDecimal getStarting_amount();
}
