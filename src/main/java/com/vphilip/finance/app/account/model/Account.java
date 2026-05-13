package com.vphilip.finance.app.account.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Table("account")
public record Account(
    @Id
    Long id,
    String name,
    String description,
    Long account_type_id,
    BigDecimal starting_amount
) {}
