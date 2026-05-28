package com.vphilip.finance.app.transfer.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "transfer")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "from_account_id")
    private Long from_account_id;

    @Column(name = "to_account_id")
    private Long to_account_id;

    @Column(name = "description")
    private String description;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "transfer_category_id")
    private Long transfer_category_id;

    @Column(name = "transaction_date")
    private LocalDate transaction_date;

    @Column(name = "created_at")
    private LocalDateTime created_at;

    @Column(name = "last_modified_at")
    private LocalDateTime last_modified_at;

    @Column(name = "budget_id")
    private Long budget_id;
}
