package com.vphilip.finance.app.account.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record AccountTypes(
        @JsonProperty("account_types")
        List<AccountType> accountTypes
) {}
