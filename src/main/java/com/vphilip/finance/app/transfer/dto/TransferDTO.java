package com.vphilip.finance.app.transfer.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public interface TransferDTO {
    Long getId();
    Long getFrom_account_id();
    String getFrom_account_label();
    Long getTo_account_id();
    String getTo_account_label();
    String getDescription();
    BigDecimal getAmount();
    Long getTransfer_type_id();
    String getTransfer_type_label();
    Long getTransfer_category_id();
    String getTransfer_category_label();
    LocalDate getTransaction_date();
    LocalDateTime getCreated_at();
    LocalDateTime getLast_modified_at();
}