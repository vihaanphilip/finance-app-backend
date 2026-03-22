package com.vphilip.finance.app.transfer.dto;

public record TransferDTO(
    Long id,
    Long from_account_id,
    Long to_account_id,
    String description,
    java.math.BigDecimal amount,
    Long transfer_type_id,
    String transfer_type_label,
    Long transfer_category_id,
    String transfer_category_label,
    java.util.Date transaction_date,
    java.time.LocalDateTime created_at,
    java.time.LocalDateTime last_modified_at
) {
}
