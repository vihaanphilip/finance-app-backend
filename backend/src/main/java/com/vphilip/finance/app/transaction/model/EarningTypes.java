package com.vphilip.finance.app.transaction.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record EarningTypes(
    @JsonProperty("earning_types")
    java.util.List<EarningType> earningTypes
) {
}
