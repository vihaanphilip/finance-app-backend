package com.vphilip.finance.app.transfer.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "transfer_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransferType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "label")
    private String label;

    @Column(name = "description")
    private String description;
}
