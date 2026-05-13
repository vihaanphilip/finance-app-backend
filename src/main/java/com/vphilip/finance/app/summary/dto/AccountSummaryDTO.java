package com.vphilip.finance.app.summary.dto;

import java.math.BigDecimal;

public record AccountSummaryDTO(
        Long account_id,
        String account_name,
        BigDecimal starting_amount,
        BigDecimal balance_amount,
        BigDecimal earnings_amount,
        BigDecimal expenses_amount,
        BigDecimal transfers_in_amount,
        BigDecimal transfers_out_amount
) {
}
