package com.vphilip.finance.app.expense.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "expense_category")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "expense_type_id")
    private Long expense_type_id;

    @Column(name = "label")
    private String label;

    @Column(name = "description")
    private String description;

    @Column(name = "user_id")
    private Integer user_id;

    public Long getExpenseTypeId() { return expense_type_id; }
}
