-- liquibase formatted sql

-- changeset Andrew:001-create-pharmacy-chain

CREATE TABLE pharmacy_chain
(
    id             UUID         NOT NULL DEFAULT gen_random_uuid(),
    address        VARCHAR(255) NOT NULL,
    locale         BOOLEAN      NOT NULL DEFAULT false,
    short_name     VARCHAR(32),
    juridical_name VARCHAR(64)  NOT NULL,
    juridical_form VARCHAR(8)   NOT NULL,

    CONSTRAINT pk_pharmacy_chain PRIMARY KEY (id)
);

-- rollback DROP TABLE pharmacy_chain;