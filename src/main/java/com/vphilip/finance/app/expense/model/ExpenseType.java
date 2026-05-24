package com.vphilip.finance.app.expense.model;

import jakarta.persistence.*;

@Entity
@Table(name = "expense_type")
public class ExpenseType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "label")
    private String label;

    @Column(name = "description")
    private String description;

    public ExpenseType() {}

    public ExpenseType(Long id, String label, String description) {
        this.id = id;
        this.label = label;
        this.description = description;
    }

    public Long getId() { return id; }
    public String getLabel() { return label; }
    public String getDescription() { return description; }

    public void setId(Long id) { this.id = id; }
    public void setLabel(String label) { this.label = label; }
    public void setDescription(String description) { this.description = description; }
}