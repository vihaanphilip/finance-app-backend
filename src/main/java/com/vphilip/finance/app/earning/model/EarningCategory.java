package com.vphilip.finance.app.earning.model;

import jakarta.persistence.*;

@Entity
@Table(name = "earning_category")
public class EarningCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "earning_type_id")
    private Long earning_type_id;

    @Column(name = "label")
    private String label;

    @Column(name = "description")
    private String description;

    public EarningCategory() {}

    public EarningCategory(Long id, Long earning_type_id, String label, String description) {
        this.id = id;
        this.earning_type_id = earning_type_id;
        this.label = label;
        this.description = description;
    }

    public Long getId() { return id; }
    public Long getEarningTypeId() { return earning_type_id; }
    public String getLabel() { return label; }
    public String getDescription() { return description; }

    public void setId(Long id) { this.id = id; }
    public void setEarning_type_id(Long earning_type_id) { this.earning_type_id = earning_type_id; }
    public void setLabel(String label) { this.label = label; }
    public void setDescription(String description) { this.description = description; }
}