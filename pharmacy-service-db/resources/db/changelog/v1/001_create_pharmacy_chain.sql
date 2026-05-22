-- liquibase formatted sql

-- changeset Andrew:001_create_pharmacy_chain
-- comment: Создание таблицы аптечных сетей. Содержит информацию о сетях аптек.

CREATE TABLE pharmacy_chain
(
    id             UUID         NOT NULL DEFAULT gen_random_uuid(),
    address        VARCHAR(255) NOT NULL,
    locale         BOOLEAN      NOT NULL DEFAULT false,
    short_name     VARCHAR(32),
    inn            VARCHAR(12)  NOT NULL,
    juridical_name VARCHAR(64)  NOT NULL,
    juridical_form VARCHAR(8)   NOT NULL,

    CONSTRAINT pk_pharmacy_chain PRIMARY KEY (id),
    CONSTRAINT uq_pharmacy_chain_inn UNIQUE (inn)
);

COMMENT ON TABLE pharmacy_chain IS 'Аптечные сети';
COMMENT ON COLUMN pharmacy_chain.id IS 'Уникальный идентификатор сети аптек (UUID)';
COMMENT ON COLUMN pharmacy_chain.address IS 'Юридический адрес сети аптек';
COMMENT ON COLUMN pharmacy_chain.locale IS 'Флаг локализации: false - международная сеть, true - локальная сеть';
COMMENT ON COLUMN pharmacy_chain.short_name IS 'Краткое название сети';
COMMENT ON COLUMN pharmacy_chain.inn IS 'ИНН аптечной сети (уникальный)';
COMMENT ON COLUMN pharmacy_chain.juridical_name IS 'Полное юридическое название организации';
COMMENT ON COLUMN pharmacy_chain.juridical_form IS 'Юридическая форма (ООО, ПАО, АО, ИП)';

-- Служебная запись - "Без аптечной сети"
-- Используется для аптек, которые не привязаны ни к одной реальной аптечной сети
INSERT INTO pharmacy_chain (id, address, locale, short_name, inn, juridical_name, juridical_form)
VALUES (
        '00000000-0000-0000-0000-000000000000',
        'Служебная запись "Без аптечной сети" - не удалять',
        false,
        'Не аптечная сеть',
        '000000000000',
        'Служебная запись "Без аптечной сети"',
        'ООО'
       ) ON CONFLICT (id) DO NOTHING;

-- Функция проверяет не пытаются ли изменить или удалить служебную запись "Без аптечной сети"
CREATE OR REPLACE FUNCTION protect_default_pharmacy_chain()
RETURNS TRIGGER AS $$
BEGIN
    IF OLD.id = '00000000-0000-0000-0000-000000000000' THEN
        RAISE EXCEPTION 'Данная аптечная сеть (служебная запись) не может быть изменена или удалена';
END IF;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_protect_default_pharmacy_chain
    BEFORE UPDATE OR DELETE ON pharmacy_chain
    FOR EACH ROW
    EXECUTE FUNCTION protect_default_pharmacy_chain();

-- rollback DROP TRIGGER IF EXISTS trg_protect_default_pharmacy_chain ON pharmacy_chain;
-- rollback DROP FUNCTION IF EXISTS protect_default_pharmacy_chain();
-- rollback DROP TABLE pharmacy_chain;