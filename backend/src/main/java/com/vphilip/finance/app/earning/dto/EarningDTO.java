package com.vphilip.finance.app.earning.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

public record EarningDTO(
    Long id,
    Long account_id,
    String account_label,
    String description,
    BigDecimal amount,
    Long earning_type_id,
    String earning_type_label,
    Long earning_category_id,
    String earning_category_label,
    Date transaction_date,
    LocalDateTime created_at,
    LocalDateTime last_modified_at
) {}
