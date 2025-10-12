--CREATE TABLE IF NOT EXISTS account_type (
--    id BIGSERIAL PRIMARY KEY,
--    label VARCHAR(500) NOT NULL,
--    description VARCHAR(2000) NOT NULL
--);
--
--CREATE TABLE IF NOT EXISTS account (
--    id BIGSERIAL PRIMARY KEY,
--    account_name VARCHAR(500) NOT NULL,
--    account_type_id BIGINT NOT NULL
--);

--ALTER TABLE account
--RENAME COLUMN account_name TO name;
--
--ALTER TABLE account
--ADD COLUMN description VARCHAR(2000) NOT NULL DEFAULT '';


CREATE TABLE IF NOT EXISTS account_type (
    id BIGINT PRIMARY KEY,
    label VARCHAR(500) NOT NULL,
    description VARCHAR(2000) NOT NULL
);

CREATE TABLE IF NOT EXISTS account (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(500) NOT NULL,
    description VARCHAR(2000) NOT NULL,
    account_type_id BIGINT NOT NULL
);

-- 12/10/2025 - Adding earning_type table

CREATE TABLE IF NOT EXISTS earning_type (
    id BIGINT PRIMARY KEY,
    label VARCHAR(500) NOT NULL,
    description VARCHAR(2000) NOT NULL
);