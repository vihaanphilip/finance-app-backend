package com.vphilip.finance.app.account.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "account")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "account_type_id")
    private Long account_type_id;

    @Column(name = "starting_amount")
    private BigDecimal starting_amount;

    @Column(name = "user_id")
    private Integer user_id;
}