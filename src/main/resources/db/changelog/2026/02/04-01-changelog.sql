-- liquibase formatted sql

-- changeset andrew:1770187409247-6
ALTER TABLE cooking_steps
    ADD cooking_step_title VARCHAR(100);

