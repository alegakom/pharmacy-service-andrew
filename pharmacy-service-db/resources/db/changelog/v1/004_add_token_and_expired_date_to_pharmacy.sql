-- liquibase formatted sql

-- changeset Andrew:004_add_token_and_expired_date_to_pharmacy
-- comment: Добавление полей token и expire_date в таблицу pharmacy

ALTER TABLE pharmacy
    ADD COLUMN token VARCHAR(32),
    ADD COLUMN expired_date DATE;

-- rollback ALTER TABLE pharmacy DROP COLUMN token, DROP COLUMN expire_date;