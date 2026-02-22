-- =============================================================
-- Modern Bookstore — Seed Data Script
-- Run AFTER 01_create_tables.sql
-- =============================================================

-- -------------------------------------------------------------
-- Roles
-- -------------------------------------------------------------
INSERT INTO role (name) VALUES ('ROLE_ADMIN')    ON CONFLICT DO NOTHING;
INSERT INTO role (name) VALUES ('ROLE_EMPLOYEE') ON CONFLICT DO NOTHING;
INSERT INTO role (name) VALUES ('ROLE_USER')     ON CONFLICT DO NOTHING;

-- -------------------------------------------------------------
-- Default Admin User
-- username : admin
-- password : Admin123   (BCrypt hash below)
-- -------------------------------------------------------------
INSERT INTO app_user (username, password, firstname, lastname, email)
VALUES (
    'admin',
    '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
    'System', 'Admin', 'admin@bookstore.com'
) ON CONFLICT DO NOTHING;

INSERT INTO user_roles (username, role_id)
SELECT 'admin', id FROM role WHERE name = 'ROLE_ADMIN'
ON CONFLICT DO NOTHING;

-- -------------------------------------------------------------
-- Default Employee User
-- username : employee1
-- password : Employee123   (BCrypt hash below)
-- -------------------------------------------------------------
INSERT INTO app_user (username, password, firstname, lastname, email)
VALUES (
    'employee1',
    '$2a$10$GbhOlqWS9g5GzJCXu6.m8eifT6S0STpD.e4yJdwD/fIbGPjBGXVUy',
    'Jane', 'Smith', 'employee1@bookstore.com'
) ON CONFLICT DO NOTHING;

INSERT INTO user_roles (username, role_id)
SELECT 'employee1', id FROM role WHERE name = 'ROLE_EMPLOYEE'
ON CONFLICT DO NOTHING;

-- -------------------------------------------------------------
-- Sample Books  (optional — for demo/testing)
-- -------------------------------------------------------------
INSERT INTO book (title, author, genre, description, copy, price) VALUES
('Clean Code',                'Robert C. Martin', 'Technology',  'A handbook of agile software craftsmanship.',          5,  29.99),
('The Pragmatic Programmer',  'David Thomas',     'Technology',  'From journeyman to master.',                           3,  35.00),
('Design Patterns',           'Gang of Four',     'Technology',  'Elements of reusable object-oriented software.',       4,  45.00),
('Sapiens',                   'Yuval Noah Harari','History',     'A brief history of humankind.',                        8,  18.99),
('The Great Gatsby',          'F. Scott Fitzgerald','Fiction',   'A story of the fabulously wealthy Jay Gatsby.',        10, 12.99),
('To Kill a Mockingbird',     'Harper Lee',       'Fiction',     'A gripping tale about race and justice.',              7,  14.99),
('Atomic Habits',             'James Clear',      'Self-Help',   'Tiny changes, remarkable results.',                    6,  22.50),
('The Alchemist',             'Paulo Coelho',     'Fiction',     'A journey of self-discovery and destiny.',             12, 15.00)
ON CONFLICT DO NOTHING;

-- -------------------------------------------------------------
-- Sample Employees  (optional — for demo/testing)
-- -------------------------------------------------------------
INSERT INTO employee (name, salary, department) VALUES
('Alice Johnson', 55000.00, 'Sales'),
('Bob Williams',  62000.00, 'IT'),
('Carol Davis',   48000.00, 'Warehouse'),
('David Brown',   71000.00, 'Management')
ON CONFLICT DO NOTHING;

-- =============================================================
-- Verify seed data
-- =============================================================
SELECT 'books'     AS "table", COUNT(*) AS rows FROM book
UNION ALL
SELECT 'employees',            COUNT(*)         FROM employee
UNION ALL
SELECT 'app_user',             COUNT(*)         FROM app_user
UNION ALL
SELECT 'roles',                COUNT(*)         FROM role;
