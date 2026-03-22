package com.vphilip.finance.app.transfer.dto;

public record TransferCategoryDTO(
        Long id,
        Long transfer_type_id,
        String label,
        String description,
        String transfer_type_label
) {}
