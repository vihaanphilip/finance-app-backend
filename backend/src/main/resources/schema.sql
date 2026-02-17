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

-- 18/02/2026 - Add transaction_date column to earning table
--ALTER TABLE earning
--ADD COLUMN transaction_date DATE NOT NULL DEFAULT CURRENT_DATE;


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

---- 12/10/2025 - Adding earning_category table
CREATE TABLE IF NOT EXISTS earning_category (
    id BIGSERIAL PRIMARY KEY,
    label VARCHAR(500) NOT NULL,
    description VARCHAR(2000) NOT NULL,
    earning_type_id BIGINT NOT NULL
);

-- 02/11/2025 - Adding earning table
CREATE TABLE IF NOT EXISTS earning (
    id BIGSERIAL PRIMARY KEY,
    account_id BIGINT NOT NULL,
    description VARCHAR(2000) NOT NULL,
    amount DECIMAL(19,2) NOT NULL,
    earning_type_id BIGINT NOT NULL,
    earning_category_id BIGINT NOT NULL,
    transaction_date DATE NOT NULL DEFAULT CURRENT_DATE,
    created_at TIMESTAMP NOT NULL,
    last_modified_at TIMESTAMP NOT NULL,
    FOREIGN KEY (account_id) REFERENCES account(id),
    FOREIGN KEY (earning_type_id) REFERENCES earning_type(id),
    FOREIGN KEY (earning_category_id) REFERENCES earning_category(id)
);