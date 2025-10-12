package com.vphilip.finance.app.transaction.model;

import org.springframework.data.annotation.Id;

public record EarningCategory(
        @Id
        Long id,
        Long earning_type_id,
        String label,
        String description
) {
    public Long getId() {
        return id;
    }

    public Long getEarningTypeId() {
        return earning_type_id;
    }

    public String getLabel() {
        return label;
    }

    public String getDescription() {
        return description;
    }
}