package com.vphilip.finance.app.account.model;

public record AccountDTO(
    Long id,
    String name,
    String description,
    Long account_type_id,
    String account_type_label
) {}
