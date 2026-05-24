package com.vphilip.finance.app.expense.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "expense_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "label")
    private String label;

    @Column(name = "description")
    private String description;
}
