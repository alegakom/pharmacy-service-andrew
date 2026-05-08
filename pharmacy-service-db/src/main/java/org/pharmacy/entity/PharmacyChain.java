package org.pharmacy.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Сущность "Аптечная сеть".
 */
@Entity
@Table(name = "pharmacy_chain")
@Getter
@Setter
@NoArgsConstructor
public class PharmacyChain {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private boolean locale = false;

    @Column(name = "short_name", length = 32)
    private String shortName;

    @Column(name = "juridical_name", nullable = false, length = 64)
    private String juridicalName;

    @Column(name = "juridical_form", nullable = false, length = 8)
    private String juridicalForm;

    @OneToMany(mappedBy = "pharmacyChain", cascade = CascadeType.ALL)
    private List<Pharmacy> pharmacies = new ArrayList<>();
}
