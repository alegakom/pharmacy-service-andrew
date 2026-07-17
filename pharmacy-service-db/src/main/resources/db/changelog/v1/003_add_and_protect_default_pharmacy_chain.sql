-- liquibase formatted sql

-- changeset Andrew:003_add_and_protect_default_pharmacy_chain splitStatements:false
-- comment: Добавление служебной записи "Без аптечной сети" и триггера для её защиты.

INSERT INTO pharmacy_chain (id, address, locale, short_name, inn, juridical_name, juridical_form)
VALUES ('00000000-0000-0000-0000-000000000000',
        'N/A',
        false,
        'Без аптечной сети',
        '000000000000',
        'Не привязана к аптечной сети',
        'NONE')
ON CONFLICT (id) DO NOTHING;

-- Функция для защиты
CREATE OR REPLACE FUNCTION protect_default_pharmacy_chain()
    RETURNS TRIGGER AS
$$
BEGIN
    IF OLD.id = '00000000-0000-0000-0000-000000000000' THEN
        RAISE EXCEPTION 'Служебная запись "Без аптечной сети" не может быть изменена или удалена';
    END IF;
    RETURN OlD;
END;
$$ LANGUAGE plpgsql;

-- Триггер защиты
CREATE TRIGGER trg_protect_default_pharmacy_chain
    BEFORE UPDATE OR DELETE
    ON pharmacy_chain
    FOR EACH ROW
EXECUTE FUNCTION protect_default_pharmacy_chain();

-- rollback DROP TRIGGER IF EXISTS trg_protect_default_pharmacy_chain ON pharmacy_chain;
-- rollback DROP FUNCTION IF EXISTS protect_default_pharmacy_chain();
-- rollback DELETE FROM pharmacy_chain WHERE id = '00000000-0000-0000-0000-000000000000';