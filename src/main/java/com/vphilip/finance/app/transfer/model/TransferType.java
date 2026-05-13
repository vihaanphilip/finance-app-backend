package com.vphilip.finance.app.transfer.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("transfer_type")
public record TransferType(
        @Id
        Long id,
        String label,
        String description
) {
    public Long getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public String getDescription() {
        return description;
    }
}
