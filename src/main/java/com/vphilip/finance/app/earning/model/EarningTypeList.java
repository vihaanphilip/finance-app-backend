package com.vphilip.finance.app.earning.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record EarningTypeList(
    @JsonProperty("earning_types")
    java.util.List<EarningType> earningTypes
) {
}
