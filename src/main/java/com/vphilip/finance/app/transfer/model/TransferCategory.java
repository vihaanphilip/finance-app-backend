package com.vphilip.finance.app.transfer.model;

import jakarta.persistence.*;

@Entity
@Table(name = "transfer_category")
public class TransferCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transfer_type_id")
    private Long transfer_type_id;

    @Column(name = "label")
    private String label;

    @Column(name = "description")
    private String description;

    public TransferCategory() {}

    public TransferCategory(Long id, Long transfer_type_id, String label, String description) {
        this.id = id;
        this.transfer_type_id = transfer_type_id;
        this.label = label;
        this.description = description;
    }

    public Long getId() { return id; }
    public Long getTransfer_type_id() { return transfer_type_id; }
    public String getLabel() { return label; }
    public String getDescription() { return description; }

    public void setId(Long id) { this.id = id; }
    public void setTransfer_type_id(Long transfer_type_id) { this.transfer_type_id = transfer_type_id; }
    public void setLabel(String label) { this.label = label; }
    public void setDescription(String description) { this.description = description; }
}