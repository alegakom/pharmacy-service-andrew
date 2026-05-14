-- liquibase formatted sql

-- changeset Andrew:002_create_pharmacy

CREATE TABLE pharmacy
(
    id                UUID         NOT NULL DEFAULT gen_random_uuid(),
    address           VARCHAR(255) NOT NULL,
    name              VARCHAR(64)  NOT NULL,
    inn               BIGINT       NOT NULL,
    category          VARCHAR(5)   NOT NULL,
    juridical_form    VARCHAR(8)   NOT NULL,
    pharmacy_chain_id UUID,

    CONSTRAINT pk_pharmacy PRIMARY KEY (id),

    CONSTRAINT fk_pharmacy_chain FOREIGN KEY (pharmacy_chain_id)
        REFERENCES pharmacy_chain (id) ON DELETE SET NULL
);

-- rollback DROP TABLE pharmacy;