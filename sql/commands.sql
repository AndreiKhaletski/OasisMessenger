CREATE ROLE system_app WITH
	LOGIN
	NOSUPERUSER
	CREATEDB
	NOCREATEROLE
	INHERIT
	NOREPLICATION
	NOBYPASSRLS
	CONNECTION LIMIT -1
	PASSWORD '855312';

CREATE DATABASE dboasis
	WITH
	OWNER=system_app
	ENCODING='UTF8'
	CONNECTION LIMIT=-1
	IS_TEMPLATE=False;

CREATE SCHEMA app AUTHORIZATION "system_app";

CREATE TABLE IF NOT EXISTS app.users (
    uuid UUID PRIMARY KEY,
    email VARCHAR(255),
    password VARCHAR(255),
    role VARCHAR(255),
    name VARCHAR(255),
    surname VARCHAR(255),
    nickname VARCHAR(255),
    birth_day TIMESTAMP WITH TIME ZONE NOT NULL,
    dt_create TIMESTAMP WITH TIME ZONE,
    dt_update TIMESTAMP WITH TIME ZONE,
    status VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS app.verification (
    email VARCHAR(255) PRIMARY KEY,
    code VARCHAR(255),
    status VARCHAR(255)
);

ALTER TABLE app.verification
    OWNER TO "system_app";

INSERT INTO app.users (
    uuid,
    email,
    password,
    role,
    name,
    surname,
    nickname,
    birth_day,
    dt_create,
    dt_update,
    status
)
VALUES (
    '00000000-0000-0000-0000-000000000000',
    'admin@gmail.com',
    '$2a$10$kVx33idOsssroHLhLR7Bgu1WkJQ.N3Cy0Ma3u6Lcy.8GPuxwnbxmq',
    'ROLE_ADMIN',
    'Admin',
    'Administrator',
    'Adminovich',
    '1995-08-21',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    'ACTIVATED'
);
