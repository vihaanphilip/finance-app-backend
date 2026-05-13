package com.vphilip.finance.app.earning.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record EarningCategoryList(
    @JsonProperty("earning_categories")
    java.util.List<EarningCategory> earningCategories
) {
}
