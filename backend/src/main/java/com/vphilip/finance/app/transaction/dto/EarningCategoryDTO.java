package com.vphilip.finance.app.transaction.dto;

public record EarningCategoryDTO(
        Long id,
        Long earning_type_id,
        String label,
        String description,
        String earning_type_label
) {}