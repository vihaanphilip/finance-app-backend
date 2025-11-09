package com.vphilip.finance.app.earning.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record Earnings(
    @JsonProperty("earnings")
    List<Earning> earnings
) {}
