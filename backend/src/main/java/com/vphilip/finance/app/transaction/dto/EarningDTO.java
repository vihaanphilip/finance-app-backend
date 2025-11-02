package com.vphilip.finance.app.transaction.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record EarningDTO(
    Long id,
    Long accountId,
    String description,
    BigDecimal amount,
    Long earningTypeId,
    String earningTypeLabel,
    Long earningCategoryId,
    String earningCategoryLabel,
    LocalDateTime createdAt,
    LocalDateTime lastModifiedAt
) {}
