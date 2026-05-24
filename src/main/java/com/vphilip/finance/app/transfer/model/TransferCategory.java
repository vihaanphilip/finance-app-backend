package com.vphilip.finance.app.transfer.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "transfer_category")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
}
