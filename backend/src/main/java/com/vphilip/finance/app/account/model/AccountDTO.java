package com.vphilip.finance.app.account.model;

public record AccountDTO(
    Integer id,
    String name,
    String description,
    Integer account_type_id,
    String account_type_label
) {}
