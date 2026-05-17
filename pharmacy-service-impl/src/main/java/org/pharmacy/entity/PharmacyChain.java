package org.pharmacy.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
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
    private Boolean locale = false;

    @Column(name = "short_name", length = 32)
    private String shortName;

    @Column(nullable = false)
    @Pattern(regexp = "\\d{10}|\\d{12}")
    private String inn;

    @Column(name = "juridical_name", nullable = false, length = 64)
    private String juridicalName;

    @Column(name = "juridical_form", nullable = false, length = 8)
    private String juridicalForm;

    @OneToMany(mappedBy = "pharmacyChain", cascade = CascadeType.ALL)
    private List<Pharmacy> pharmacies = new ArrayList<>();
}
