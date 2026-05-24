package com.vphilip.finance.app.summary.dto;

import java.math.BigDecimal;

public interface AccountSummaryDTO {
    Long getAccount_id();
    String getAccount_name();
    BigDecimal getStarting_amount();
    BigDecimal getBalance_amount();
    BigDecimal getEarnings_amount();
    BigDecimal getExpenses_amount();
    BigDecimal getTransfers_in_amount();
    BigDecimal getTransfers_out_amount();
}