package com.vphilip.finance.app.earning.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public record EarningCategorySummary(
        LocalDate month,
        Long earning_category_id,
        String earning_category_label,
        BigDecimal total_earnings
) {
}
