package com.vphilip.finance.app.earning.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "earning_category")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    @Column(name = "user_id")
    private Integer user_id;

    public Long getEarningTypeId() { return earning_type_id; }
}
