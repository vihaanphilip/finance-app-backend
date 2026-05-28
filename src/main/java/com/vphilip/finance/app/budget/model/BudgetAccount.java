package com.vphilip.finance.app.budget.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "budget_account", uniqueConstraints = @UniqueConstraint(columnNames = {"budget_id", "account_id"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BudgetAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "budget_id")
    private Long budget_id;

    @Column(name = "account_id")
    private Long account_id;
}
