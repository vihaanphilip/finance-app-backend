package com.vphilip.finance.app.account.model;

import java.math.BigDecimal;

public interface AccountDTO {
    Long getId();
    String getName();
    String getDescription();
    Long getAccount_type_id();
    String getAccount_type_label();
    BigDecimal getStarting_amount();
}