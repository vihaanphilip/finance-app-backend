package com.vphilip.finance.app.transfer.dto;

public interface TransferCategoryDTO {
    Long getId();
    Long getTransfer_type_id();
    String getLabel();
    String getDescription();
    String getTransfer_type_label();
}