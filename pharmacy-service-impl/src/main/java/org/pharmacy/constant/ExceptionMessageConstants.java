package org.pharmacy.constant;

/**
 * Константные сообщения для Exception.
 */
public final class ExceptionMessageConstants {

    private ExceptionMessageConstants() {}

    public static final String PHARMACY_NOT_FOUND = "Аптека с id=%s отсутствует в базе данных";
    public static final String PHARMACY_CHAIN_NOT_FOUND = "Аптечная сеть с id=%s отсутствует в базе данных";
    public static final String DUPLICATE_INN = "Организация с ИНН %s уже существует";
}
