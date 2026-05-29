package com.vphilip.finance.app.budget.dto;

import java.math.BigDecimal;

public interface BudgetSummaryDTO {
    Long getId();
    String getName();
    BigDecimal getStarting_amount();
    BigDecimal getEntries_in();
    BigDecimal getEntries_out();
    BigDecimal getAttributed_earnings();
    BigDecimal getAttributed_expenses();
    BigDecimal getAttributed_transfers_net();
    BigDecimal getCurrent_value();
}
