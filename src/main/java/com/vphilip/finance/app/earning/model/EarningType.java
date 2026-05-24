package com.vphilip.finance.app.earning.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "earning_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EarningType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "label")
    private String label;

    @Column(name = "description")
    private String description;
}
