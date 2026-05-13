package com.vphilip.finance.app.account.model;

import java.math.BigDecimal;

public record AccountDTO(
    Long id,
    String name,
    String description,
    Long account_type_id,
    String account_type_label,
    BigDecimal starting_amount
) {}
