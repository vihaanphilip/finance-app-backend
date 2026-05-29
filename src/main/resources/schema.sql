-- Migration: add auto-increment sequence to account_type.id. Executed in Supabase and Docker DB
CREATE SEQUENCE IF NOT EXISTS account_type_id_seq
    START WITH 5
    INCREMENT BY 1
    CACHE 1;

ALTER TABLE account_type
    ALTER COLUMN id SET DEFAULT nextval('account_type_id_seq');

ALTER SEQUENCE account_type_id_seq OWNED BY account_type.id;

SELECT setval('account_type_id_seq', GREATEST((SELECT COALESCE(MAX(id), 0) FROM account_type), 4));
