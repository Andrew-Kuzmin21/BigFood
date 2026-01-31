-- liquibase formatted sql

-- changeset andrew:1769778876157-2
CREATE SEQUENCE IF NOT EXISTS dish_type_id_seq START WITH 1 INCREMENT BY 1;

-- changeset andrew:1769778876157-3
CREATE SEQUENCE IF NOT EXISTS recipe_dish_types_id_seq START WITH 1 INCREMENT BY 1;

-- changeset andrew:1769778876157-4
CREATE TABLE dish_types
(
    id                  BIGINT      NOT NULL,
    dish_type_name      VARCHAR(50) NOT NULL,
    dish_type_parent_id BIGINT,
    CONSTRAINT pk_dish_types PRIMARY KEY (id)
);

-- changeset andrew:1769778876157-5
CREATE TABLE recipe_dish_types
(
    id           BIGINT NOT NULL,
    dish_type_id BIGINT NOT NULL,
    recipe_id    BIGINT NOT NULL,
    CONSTRAINT pk_recipe_dish_types PRIMARY KEY (id)
);

-- changeset andrew:1769778876157-6
ALTER TABLE dish_types
    ADD CONSTRAINT FK_DISH_TYPES_ON_DISH_TYPE_PARENT FOREIGN KEY (dish_type_parent_id) REFERENCES dish_types (id);

-- changeset andrew:1769778876157-7
ALTER TABLE recipe_dish_types
    ADD CONSTRAINT FK_RECIPE_DISH_TYPES_ON_DISH_TYPE FOREIGN KEY (dish_type_id) REFERENCES dish_types (id);

-- changeset andrew:1769778876157-8
ALTER TABLE recipe_dish_types
    ADD CONSTRAINT FK_RECIPE_DISH_TYPES_ON_RECIPE FOREIGN KEY (recipe_id) REFERENCES recipes (id);

