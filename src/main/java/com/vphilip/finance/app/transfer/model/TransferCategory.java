package com.vphilip.finance.app.transfer.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("transfer_category")
public record TransferCategory(
        @Id
        Long id,
        Long transfer_type_id,
        String label,
        String description
) {
    public Long getId() { return id; }
    public Long getTransfer_type_id() { return transfer_type_id; }
    public String getLabel() { return label; }
    public String getDescription() { return description; }
}
