package com.vphilip.finance.app.account.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record Accounts(
        @JsonProperty("accounts")
        List<Account> accounts
) {}
