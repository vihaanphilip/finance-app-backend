package com.vphilip.finance.app.transaction.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record EarningCategories(
    @JsonProperty("earning_categories")
    java.util.List<EarningCategory> earningCategories
) {
}
