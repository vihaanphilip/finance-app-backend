package com.vphilip.finance.app.expense.model;

import jakarta.persistence.*;

@Entity
@Table(name = "expense_category")
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

    public ExpenseCategory() {}

    public ExpenseCategory(Long id, Long expense_type_id, String label, String description) {
        this.id = id;
        this.expense_type_id = expense_type_id;
        this.label = label;
        this.description = description;
    }

    public Long getId() { return id; }
    public Long getExpenseTypeId() { return expense_type_id; }
    public String getLabel() { return label; }
    public String getDescription() { return description; }

    public void setId(Long id) { this.id = id; }
    public void setExpense_type_id(Long expense_type_id) { this.expense_type_id = expense_type_id; }
    public void setLabel(String label) { this.label = label; }
    public void setDescription(String description) { this.description = description; }
}