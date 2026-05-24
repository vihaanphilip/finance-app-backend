package com.vphilip.finance.app.earning.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public interface EarningDTO {
    Long getId();
    Long getAccount_id();
    String getAccount_label();
    String getDescription();
    BigDecimal getAmount();
    Long getEarning_type_id();
    String getEarning_type_label();
    Long getEarning_category_id();
    String getEarning_category_label();
    LocalDate getTransaction_date();
    LocalDateTime getCreated_at();
    LocalDateTime getLast_modified_at();
}