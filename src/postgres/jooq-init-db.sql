CREATE ROLE jooq
    WITH LOGIN PASSWORD 'jooq'
    NOSUPERUSER INHERIT NOCREATEDB NOCREATEROLE NOREPLICATION;
CREATE DATABASE jooq_db
    WITH
    OWNER = jooq
    ENCODING = 'UTF-8'
    LC_COLLATE = 'en_US.utf8'
    LC_CTYPE = 'en_US.utf8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;
GRANT ALL PRIVILEGES ON DATABASE jooq_db TO root;

\connect jooq_db jooq
CREATE TABLE city (
    city_id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    state VARCHAR(255) NOT NULL
);
INSERT INTO city (city_id, name, state) VALUES (1, 'Raleigh', 'NC');
INSERT INTO city (city_id, name, state) VALUES (2, 'Mountain View', 'CA');
INSERT INTO city (city_id, name, state) VALUES (3, 'Knoxville', 'TN');
INSERT INTO city (city_id, name, state) VALUES (4, 'Houston', 'TX');
INSERT INTO city (city_id, name, state) VALUES (5, 'Olympia', 'WA');
INSERT INTO city (city_id, name, state) VALUES (6, 'Bismarck', 'ND');