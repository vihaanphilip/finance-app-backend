package com.vphilip.finance.app.earning.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface EarningCategorySummary {
    LocalDate getMonth();
    Long getEarning_category_id();
    String getEarning_category_label();
    BigDecimal getTotal_earnings();
}