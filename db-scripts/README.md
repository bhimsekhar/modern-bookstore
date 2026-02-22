# Database Scripts

## Connection Details
| Property | Value |
|---|---|
| Host | localhost |
| Port | 5432 |
| Database | bookstore |
| Username | postgres |
| Password | Tata@123 |

## Scripts

| File | Purpose |
|---|---|
| `01_create_tables.sql` | Creates all 7 tables (safe — uses `IF NOT EXISTS`) |
| `02_seed_data.sql` | Seeds roles, default users, and sample data |
| `03_drop_all.sql` | Drops all tables (use for clean reset only) |

## How to Run

### Option A — psql command line
```bash
# Step 1: Create tables
psql -U postgres -h localhost -p 5432 -d bookstore -f 01_create_tables.sql

# Step 2: Seed data
psql -U postgres -h localhost -p 5432 -d bookstore -f 02_seed_data.sql
```

### Option B — pgAdmin
1. Open pgAdmin → connect to `bookstore` database
2. Open Query Tool (Tools → Query Tool)
3. Open `01_create_tables.sql` → Run (F5)
4. Open `02_seed_data.sql` → Run (F5)

### Option C — DBeaver / any SQL client
1. Connect to `localhost:5432/bookstore` with user `postgres` / `Tata@123`
2. Run `01_create_tables.sql` then `02_seed_data.sql`

## After Running Scripts

Update `backend/src/main/resources/application.yml`:
```yaml
spring:
  jpa:
    hibernate:
      ddl-auto: validate   # change from 'update' to 'validate'
```
This tells JPA to validate the schema matches entities (not recreate it).

## Default Login Credentials

| Username | Password | Role |
|---|---|---|
| `admin` | `Admin123` | ROLE_ADMIN — full access |
| `employee1` | `Employee123` | ROLE_EMPLOYEE — purchases only |

## Tables Created

```
bookstore database
├── book              — book inventory
├── employee          — staff records
├── purchase_detail   — sales transactions
├── feedback          — customer feedback
├── app_user          — registered users (NOT 'user' — reserved word in PostgreSQL)
├── role              — ROLE_ADMIN, ROLE_EMPLOYEE, ROLE_USER
└── user_roles        — join table (app_user ↔ role)
```
