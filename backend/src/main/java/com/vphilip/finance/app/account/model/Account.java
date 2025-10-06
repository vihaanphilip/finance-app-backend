package com.vphilip.finance.app.account.model;

import org.springframework.data.annotation.Id;

public record Account(
    @Id
    Long id,
    String name,
    String description,
    Long account_type_id
) {}
