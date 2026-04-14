-- liquibase formatted sql

-- changeset author-name:kashirskiyaa: 20260404_create_table_credential_auth

CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE credential
(
    id           UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    series       VARCHAR(16) NOT NULL UNIQUE,
    token        VARCHAR(32) NOT NULL,
    expired_date TIMESTAMP   NOT NULL,
    active       BOOLEAN     NOT NULL DEFAULT true,
    updated_date TIMESTAMP   NOT NULL
);