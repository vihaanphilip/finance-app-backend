package com.vphilip.finance.app.budget.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Check;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "budget_entry")
@Check(constraints = "direction in ('IN','OUT')")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BudgetEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "budget_id")
    private Long budget_id;

    @Column(name = "direction", length = 3)
    private String direction;

    @Column(name = "amount", precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(name = "description", length = 2000)
    private String description;

    @Column(name = "transaction_date")
    private LocalDate transaction_date;

    @Column(name = "created_at")
    private LocalDateTime created_at;

    @Column(name = "last_modified_at")
    private LocalDateTime last_modified_at;
}
