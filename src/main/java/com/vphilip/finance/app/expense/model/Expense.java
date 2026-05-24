package com.vphilip.finance.app.expense.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "expense")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_id")
    private Long account_id;

    @Column(name = "description")
    private String description;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "expense_category_id")
    private Long expense_category_id;

    @Column(name = "transaction_date")
    private LocalDate transaction_date;

    @Column(name = "created_at")
    private LocalDateTime created_at;

    @Column(name = "last_modified_at")
    private LocalDateTime last_modified_at;
}
