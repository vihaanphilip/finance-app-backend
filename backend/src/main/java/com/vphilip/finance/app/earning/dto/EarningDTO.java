package com.vphilip.finance.app.earning.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
    LocalDateTime created_at,
    LocalDateTime last_modified_at
) {}
