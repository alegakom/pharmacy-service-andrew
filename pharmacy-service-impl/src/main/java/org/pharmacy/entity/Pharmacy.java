package org.pharmacy.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
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

    @Column(nullable = false, unique = true)
    @Pattern(regexp = "\\d{10}|\\d{12}")
    private String inn;

    @Column(nullable = false, length = 5)
    private String category;

    @Column(name = "juridical_form", nullable = false, length = 8)
    private String juridicalForm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pharmacy_chain_id")
    private PharmacyChain pharmacyChain;
}
