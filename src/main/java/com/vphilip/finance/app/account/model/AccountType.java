package com.vphilip.finance.app.account.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "account_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "label")
    private String label;

    @Column(name = "description")
    private String description;
}