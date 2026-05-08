package org.pharmacy.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * Сущность "Аптека".
 */
@Entity
@Table(name = "pharmacy")
@Getter
@Setter
@NoArgsConstructor
public class Pharmacy {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false, length = 64)
    private String name;

    @Column(nullable = false)
    private Long inn;

    @Column(nullable = false, length = 5)
    private String category;

    @Column(name = "juridical_form", nullable = false, length = 8)
    private String juridicalForm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pharmacy_chain_id")
    private PharmacyChain pharmacyChain;
}
