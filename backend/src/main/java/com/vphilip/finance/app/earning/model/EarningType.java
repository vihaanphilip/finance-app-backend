package com.vphilip.finance.app.earning.model;

import org.springframework.data.annotation.Id;

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
