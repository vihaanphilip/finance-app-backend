package com.vphilip.finance.app.earning.dto;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import com.vphilip.finance.app.earning.model.Earning;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class EarningCsvDto {
    @CsvBindByName
    private Long id;

    @CsvBindByName(column = "account_id")
    private Long account_id;

    @CsvBindByName
    private String description;

    @CsvBindByName
    private BigDecimal amount;

    @CsvBindByName(column = "earning_type_id")
    private Long earning_type_id;

    @CsvBindByName(column = "earning_category_id")
    private Long earning_category_id;

    @CsvBindByName(column = "transaction_date")
    @CsvDate(value = "yyyy-MM-dd")
    private LocalDate transaction_date;

    @CsvBindByName(column = "created_at")
    @CsvDate(value = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime created_at;

    @CsvBindByName(column = "last_modified_at")
    @CsvDate(value = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime last_modified_at;

    // Convert to Earning
    public Earning toEarning() {
        return new Earning(
            id,
            account_id,
            description,
            amount,
            earning_type_id,
            earning_category_id,
            transaction_date,
            created_at,
            last_modified_at
        );
    }
}
