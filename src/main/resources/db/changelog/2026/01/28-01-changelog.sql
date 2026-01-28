-- liquibase formatted sql

-- changeset andrew:1769590581579-1
ALTER TABLE users
    ALTER COLUMN user_email DROP NOT NULL;

