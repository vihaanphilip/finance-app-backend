package com.vphilip.finance.app.earning.model;

import org.springframework.data.annotation.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Earning(
        @Id
        Long id,
        Long account_id,
        String description,
        BigDecimal amount,
        Long earning_type_id,
        Long earning_category_id,
        LocalDateTime created_at,
        LocalDateTime last_modified_at
) {
        public Long getId() {
                return id;
        }
}
