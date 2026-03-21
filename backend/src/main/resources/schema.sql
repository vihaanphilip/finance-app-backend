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

-- 12/10/2025 - Remove earning_type_id column to earning table
--BEGIN;
-- Drop FK (adjust constraint name if yours differs)
--ALTER TABLE earning DROP CONSTRAINT IF EXISTS earning_earning_type_id_fkey;
-- Drop the column
--ALTER TABLE earning DROP COLUMN IF EXISTS earning_type_id;
--COMMIT;

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
    id BIGINT PRIMARY KEY,
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
    earning_category_id BIGINT NOT NULL,
    transaction_date DATE NOT NULL DEFAULT CURRENT_DATE,
    created_at TIMESTAMP NOT NULL,
    last_modified_at TIMESTAMP NOT NULL,
    FOREIGN KEY (account_id) REFERENCES account(id),
    FOREIGN KEY (earning_category_id) REFERENCES earning_category(id)
);

-- 18/02/2026 - Add expense_type table
CREATE TABLE IF NOT EXISTS expense_type (
    id BIGINT PRIMARY KEY,
    label VARCHAR(500) NOT NULL,
    description VARCHAR(2000) NOT NULL
);

-- 18/02/2026 - Add expense_category table
CREATE TABLE IF NOT EXISTS expense_category (
    id BIGINT PRIMARY KEY,
    label VARCHAR(500) NOT NULL,
    description VARCHAR(2000) NOT NULL,
    expense_type_id BIGINT NOT NULL
);

-- 18/02/2026 - Add expense table
CREATE TABLE IF NOT EXISTS expense (
    id BIGSERIAL PRIMARY KEY,
    account_id BIGINT NOT NULL,
    description VARCHAR(2000) NOT NULL,
    amount DECIMAL(19,2) NOT NULL,
    expense_category_id BIGINT NOT NULL,
    transaction_date DATE NOT NULL DEFAULT CURRENT_DATE,
    created_at TIMESTAMP NOT NULL,
    last_modified_at TIMESTAMP NOT NULL,
    FOREIGN KEY (account_id) REFERENCES account(id),
    FOREIGN KEY (expense_category_id) REFERENCES expense_category(id)
);