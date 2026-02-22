-- =============================================================
-- Modern Bookstore — DROP ALL TABLES (for reset/clean rebuild)
-- WARNING: This permanently deletes ALL data.
-- =============================================================

DROP TABLE IF EXISTS user_roles     CASCADE;
DROP TABLE IF EXISTS app_user       CASCADE;
DROP TABLE IF EXISTS role           CASCADE;
DROP TABLE IF EXISTS feedback       CASCADE;
DROP TABLE IF EXISTS purchase_detail CASCADE;
DROP TABLE IF EXISTS employee       CASCADE;
DROP TABLE IF EXISTS book           CASCADE;

SELECT 'All tables dropped.' AS status;
