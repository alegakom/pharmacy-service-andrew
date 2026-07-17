-- liquibase formatted sql

-- changeset Andrew:002_create_table_pharmacy
-- comment: Создание таблицы аптек. Содержит основную информацию об аптечных точках.

CREATE TABLE pharmacy
(
    id                UUID         NOT NULL DEFAULT gen_random_uuid(),
    address           VARCHAR(255) NOT NULL,
    name              VARCHAR(64)  NOT NULL,
    inn               VARCHAR(12)  NOT NULL,
    category          VARCHAR(5)   NOT NULL,
    juridical_form    VARCHAR(8)   NOT NULL,
    pharmacy_chain_id UUID,

    CONSTRAINT pk_pharmacy PRIMARY KEY (id),
    CONSTRAINT uq_pharmacy_inn UNIQUE (inn),
    CONSTRAINT fk_pharmacy_chain FOREIGN KEY (pharmacy_chain_id)
        REFERENCES pharmacy_chain (id) ON DELETE SET NULL
);

COMMENT ON TABLE pharmacy IS 'Аптеки';
COMMENT ON COLUMN pharmacy.id IS 'Уникальный идентификатор аптеки (UUID)';
COMMENT ON COLUMN pharmacy.address IS 'Физический адрес аптеки';
COMMENT ON COLUMN pharmacy.name IS 'Название аптеки';
COMMENT ON COLUMN pharmacy.inn IS 'ИНН аптеки (уникальный)';
COMMENT ON COLUMN pharmacy.category IS 'Категория аптеки';
COMMENT ON COLUMN pharmacy.juridical_form IS 'Юридическая форма (ООО, ПАО, АО, ИП)';
COMMENT ON COLUMN pharmacy.pharmacy_chain_id IS 'ID сети аптек (внешний ключ)';

-- rollback DROP TABLE pharmacy;