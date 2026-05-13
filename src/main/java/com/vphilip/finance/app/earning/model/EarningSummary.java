package com.vphilip.finance.app.earning.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record EarningSummary(
        LocalDate month,
        BigDecimal total_earnings,
        List<EarningCategorySummary> categories
) {}
