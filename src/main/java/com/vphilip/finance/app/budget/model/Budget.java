package com.vphilip.finance.app.budget.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "budget")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 500)
    private String name;

    @Column(name = "description", length = 2000)
    private String description;

    @Column(name = "starting_amount", precision = 19, scale = 2)
    private BigDecimal starting_amount;

    @Column(name = "user_id")
    private Integer user_id;
}
