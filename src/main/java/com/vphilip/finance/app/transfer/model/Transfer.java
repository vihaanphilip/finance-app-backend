package com.vphilip.finance.app.transfer.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Table("transfer")
public record Transfer(
        @Id
        Long id,
        Long from_account_id,
        Long to_account_id,
        String description,
        BigDecimal amount,
        Long transfer_category_id,
        LocalDate transaction_date,
        LocalDateTime created_at,
        LocalDateTime last_modified_at
) {
}
