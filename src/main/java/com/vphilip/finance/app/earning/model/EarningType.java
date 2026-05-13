package com.vphilip.finance.app.earning.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("earning_type")
public record EarningType(
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
