package org.pharmacy.constant;

/**
 * Константные сообщения для Exception.
 */
public final class ExceptionMessageConstants {

    private ExceptionMessageConstants() {}

    public static final String PHARMACY_NOT_FOUND = "Pharmacy not found: %s";
    public static final String PHARMACY_CHAIN_NOT_FOUND = "PharmacyChain not found: %s";
    public static final String DUPLICATE_INN = "Pharmacy with INN %s already exists";
}
