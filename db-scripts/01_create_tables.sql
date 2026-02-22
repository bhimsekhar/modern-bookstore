-- =============================================================
-- Modern Bookstore — PostgreSQL Table Creation Script
-- Database : bookstore
-- User     : postgre
-- Host     : localhost:5432
--
-- Run this script BEFORE starting the Spring Boot backend.
-- After running, set application.yml  ddl-auto: validate
-- (JPA will validate schema matches entities without recreating)
-- =============================================================

-- Connect to the bookstore database first:
--   psql -U postgre -h localhost -p 5432 -d bookstore -f 01_create_tables.sql

-- -------------------------------------------------------------
-- 1. BOOK
-- -------------------------------------------------------------
CREATE TABLE IF NOT EXISTS book (
    id          SERIAL PRIMARY KEY,
    title       VARCHAR(255) NOT NULL,
    author      VARCHAR(255) NOT NULL,
    genre       VARCHAR(100),
    description TEXT,
    copy        INTEGER      NOT NULL DEFAULT 0,
    price       DECIMAL(10,2) NOT NULL
);

-- -------------------------------------------------------------
-- 2. EMPLOYEE
-- -------------------------------------------------------------
CREATE TABLE IF NOT EXISTS employee (
    id         SERIAL PRIMARY KEY,
    name       VARCHAR(255)  NOT NULL,
    salary     DECIMAL(10,2) NOT NULL,
    department VARCHAR(100)  NOT NULL
);

-- -------------------------------------------------------------
-- 3. PURCHASE_DETAIL
--    (legacy table name kept; 'total_price' and 'date_purchased'
--     were missing from the old UI — now fully supported)
-- -------------------------------------------------------------
CREATE TABLE IF NOT EXISTS purchase_detail (
    id              SERIAL PRIMARY KEY,
    name            VARCHAR(255)  NOT NULL,
    phone           VARCHAR(20),
    books           VARCHAR(500),
    quantity        INTEGER       NOT NULL DEFAULT 1,
    total_price     DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    date_purchased  TIMESTAMP     DEFAULT CURRENT_TIMESTAMP
);

-- -------------------------------------------------------------
-- 4. FEEDBACK
-- -------------------------------------------------------------
CREATE TABLE IF NOT EXISTS feedback (
    id           SERIAL PRIMARY KEY,
    name         VARCHAR(255) NOT NULL,
    phone        VARCHAR(20),
    email        VARCHAR(255),
    feedback     TEXT         NOT NULL,
    date_created TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
);

-- -------------------------------------------------------------
-- 5. APP_USER
--    Named 'app_user' (not 'user') because USER is a reserved
--    keyword in PostgreSQL.
--    Passwords stored as BCrypt hashes (never plaintext).
--    'phone' stored as VARCHAR (legacy stored as INT — bug fixed).
-- -------------------------------------------------------------
CREATE TABLE IF NOT EXISTS app_user (
    username  VARCHAR(100) PRIMARY KEY,
    password  VARCHAR(255) NOT NULL,
    firstname VARCHAR(100),
    lastname  VARCHAR(100),
    email     VARCHAR(255) NOT NULL UNIQUE,
    address   TEXT,
    phone     VARCHAR(20)
);

-- -------------------------------------------------------------
-- 6. ROLE
-- -------------------------------------------------------------
CREATE TABLE IF NOT EXISTS role (
    id   SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

-- -------------------------------------------------------------
-- 7. USER_ROLES  (join table)
-- -------------------------------------------------------------
CREATE TABLE IF NOT EXISTS user_roles (
    username VARCHAR(100) NOT NULL,
    role_id  INTEGER      NOT NULL,
    PRIMARY KEY (username, role_id),
    CONSTRAINT fk_user_roles_user FOREIGN KEY (username)
        REFERENCES app_user(username) ON DELETE CASCADE,
    CONSTRAINT fk_user_roles_role FOREIGN KEY (role_id)
        REFERENCES role(id)           ON DELETE CASCADE
);

-- =============================================================
-- Verify tables were created
-- =============================================================
SELECT table_name
FROM   information_schema.tables
WHERE  table_schema = 'public'
ORDER  BY table_name;
