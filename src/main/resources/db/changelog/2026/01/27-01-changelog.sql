-- liquibase formatted sql

-- changeset andrew:1769504477943-1
CREATE SEQUENCE IF NOT EXISTS category_id_seq START WITH 1 INCREMENT BY 1;

-- changeset andrew:1769504477943-2
CREATE SEQUENCE IF NOT EXISTS cooking_step_id_seq START WITH 1 INCREMENT BY 1;

-- changeset andrew:1769504477943-3
CREATE SEQUENCE IF NOT EXISTS ingredient_id_seq START WITH 1 INCREMENT BY 1;

-- changeset andrew:1769504477943-4
CREATE SEQUENCE IF NOT EXISTS media_id_seq START WITH 1 INCREMENT BY 1;

-- changeset andrew:1769504477943-5
CREATE SEQUENCE IF NOT EXISTS national_cuisine_id_seq START WITH 1 INCREMENT BY 1;

-- changeset andrew:1769504477943-6
CREATE SEQUENCE IF NOT EXISTS recipe_categories_seq START WITH 1 INCREMENT BY 1;

-- changeset andrew:1769504477943-7
CREATE SEQUENCE IF NOT EXISTS recipe_id_seq START WITH 1 INCREMENT BY 1;

-- changeset andrew:1769504477943-8
CREATE SEQUENCE IF NOT EXISTS recipe_ingredients_id_seq START WITH 1 INCREMENT BY 1;

-- changeset andrew:1769504477943-9
CREATE SEQUENCE IF NOT EXISTS review_id_seq START WITH 1 INCREMENT BY 1;

-- changeset andrew:1769504477943-10
CREATE SEQUENCE IF NOT EXISTS role_id_seq START WITH 1 INCREMENT BY 1;

-- changeset andrew:1769504477943-11
CREATE SEQUENCE IF NOT EXISTS type_id_seq START WITH 1 INCREMENT BY 1;

-- changeset andrew:1769504477943-12
CREATE SEQUENCE IF NOT EXISTS unit_id_seq START WITH 1 INCREMENT BY 1;

-- changeset andrew:1769504477943-13
CREATE SEQUENCE IF NOT EXISTS user_id_seq START WITH 1 INCREMENT BY 1;

-- changeset andrew:1769504477943-14
CREATE TABLE categories
(
    id            BIGINT       NOT NULL,
    category_name VARCHAR(255) NOT NULL,
    CONSTRAINT pk_categories PRIMARY KEY (id)
);

-- changeset andrew:1769504477943-15
CREATE TABLE cooking_steps
(
    id                       BIGINT       NOT NULL,
    cooking_step_number      INTEGER      NOT NULL,
    cooking_step_description VARCHAR(500) NOT NULL,
    recipe_id                BIGINT       NOT NULL,
    CONSTRAINT pk_cooking_steps PRIMARY KEY (id)
);

-- changeset andrew:1769504477943-16
CREATE TABLE ingredients
(
    id              BIGINT       NOT NULL,
    ingredient_name VARCHAR(255) NOT NULL,
    CONSTRAINT pk_ingredients PRIMARY KEY (id)
);

-- changeset andrew:1769504477943-17
CREATE TABLE media
(
    id               BIGINT       NOT NULL,
    media_url        VARCHAR(500) NOT NULL,
    media_is_main    BOOLEAN      NOT NULL,
    media_created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    media_type_id    BIGINT,
    recipe_id        BIGINT,
    cooking_step_id  BIGINT,
    CONSTRAINT pk_media PRIMARY KEY (id)
);

-- changeset andrew:1769504477943-18
CREATE TABLE national_cuisine
(
    id                    BIGINT       NOT NULL,
    national_cuisine_name VARCHAR(255) NOT NULL,
    CONSTRAINT pk_national_cuisine PRIMARY KEY (id)
);

-- changeset andrew:1769504477943-19
CREATE TABLE recipe_categories
(
    id          BIGINT NOT NULL,
    recipe_id   BIGINT,
    category_id BIGINT,
    CONSTRAINT pk_recipe_categories PRIMARY KEY (id)
);

-- changeset andrew:1769504477943-20
CREATE TABLE recipe_ingredients
(
    id                        BIGINT         NOT NULL,
    recipe_id                 BIGINT         NOT NULL,
    ingredient_id             BIGINT         NOT NULL,
    recipe_ingredients_amount DECIMAL(10, 2) NOT NULL,
    unit_id                   BIGINT         NOT NULL,
    CONSTRAINT pk_recipe_ingredients PRIMARY KEY (id)
);

-- changeset andrew:1769504477943-21
CREATE TABLE recipes
(
    id                  BIGINT       NOT NULL,
    recipe_name         VARCHAR(100) NOT NULL,
    recipe_description  VARCHAR(700) NOT NULL,
    recipe_cooking_time INTEGER,
    recipe_serving      INTEGER,
    recipe_created_at   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    recipe_updated_at   TIMESTAMP WITHOUT TIME ZONE,
    national_cuisine_id BIGINT,
    author_id           BIGINT       NOT NULL,
    CONSTRAINT pk_recipes PRIMARY KEY (id)
);

-- changeset andrew:1769504477943-22
CREATE TABLE reviews
(
    id                 BIGINT  NOT NULL,
    review_rating      INTEGER NOT NULL,
    review_description VARCHAR(1000),
    review_created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    review_updated_at  TIMESTAMP WITHOUT TIME ZONE,
    recipe_id          BIGINT  NOT NULL,
    author_id          BIGINT  NOT NULL,
    CONSTRAINT pk_reviews PRIMARY KEY (id)
);

-- changeset andrew:1769504477943-23
CREATE TABLE roles
(
    id        BIGINT      NOT NULL,
    role_name VARCHAR(50) NOT NULL,
    CONSTRAINT pk_roles PRIMARY KEY (id)
);

-- changeset andrew:1769504477943-24
CREATE TABLE types
(
    id        BIGINT       NOT NULL,
    type_name VARCHAR(255) NOT NULL,
    CONSTRAINT pk_types PRIMARY KEY (id)
);

-- changeset andrew:1769504477943-25
CREATE TABLE units
(
    id        BIGINT       NOT NULL,
    unit_name VARCHAR(255) NOT NULL,
    CONSTRAINT pk_units PRIMARY KEY (id)
);

-- changeset andrew:1769504477943-26
CREATE TABLE users
(
    id                BIGINT       NOT NULL,
    user_username     VARCHAR(50)  NOT NULL,
    user_email        VARCHAR(50)  NOT NULL,
    user_phone_number VARCHAR(11),
    user_password     VARCHAR(100) NOT NULL,
    photo_url         VARCHAR(255),
    user_created_at   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    role_id           BIGINT,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

-- changeset andrew:1769504477943-27
ALTER TABLE cooking_steps
    ADD CONSTRAINT uc_3f14a8267aa29c105ca895288 UNIQUE (recipe_id, cooking_step_number);

-- changeset andrew:1769504477943-28
ALTER TABLE recipe_ingredients
    ADD CONSTRAINT uc_5a005819a99605d9e5d0d6e05 UNIQUE (recipe_id, ingredient_id);

-- changeset andrew:1769504477943-29
ALTER TABLE reviews
    ADD CONSTRAINT uc_bece3d39bafbc4744d7151086 UNIQUE (recipe_id, author_id);

-- changeset andrew:1769504477943-30
ALTER TABLE categories
    ADD CONSTRAINT uc_categories_category_name UNIQUE (category_name);

-- changeset andrew:1769504477943-31
ALTER TABLE ingredients
    ADD CONSTRAINT uc_ingredients_ingredient_name UNIQUE (ingredient_name);

-- changeset andrew:1769504477943-32
ALTER TABLE roles
    ADD CONSTRAINT uc_roles_role_name UNIQUE (role_name);

-- changeset andrew:1769504477943-33
ALTER TABLE types
    ADD CONSTRAINT uc_types_type_name UNIQUE (type_name);

-- changeset andrew:1769504477943-34
ALTER TABLE units
    ADD CONSTRAINT uc_units_unit_name UNIQUE (unit_name);

-- changeset andrew:1769504477943-35
ALTER TABLE users
    ADD CONSTRAINT uc_users_user_email UNIQUE (user_email);

-- changeset andrew:1769504477943-36
ALTER TABLE users
    ADD CONSTRAINT uc_users_user_phone_number UNIQUE (user_phone_number);

-- changeset andrew:1769504477943-37
ALTER TABLE users
    ADD CONSTRAINT uc_users_user_username UNIQUE (user_username);

-- changeset andrew:1769504477943-38
ALTER TABLE cooking_steps
    ADD CONSTRAINT FK_COOKING_STEPS_ON_RECIPE FOREIGN KEY (recipe_id) REFERENCES recipes (id);

-- changeset andrew:1769504477943-39
ALTER TABLE media
    ADD CONSTRAINT FK_MEDIA_ON_COOKING_STEP FOREIGN KEY (cooking_step_id) REFERENCES cooking_steps (id);

-- changeset andrew:1769504477943-40
ALTER TABLE media
    ADD CONSTRAINT FK_MEDIA_ON_MEDIA_TYPE FOREIGN KEY (media_type_id) REFERENCES types (id);

-- changeset andrew:1769504477943-41
ALTER TABLE media
    ADD CONSTRAINT FK_MEDIA_ON_RECIPE FOREIGN KEY (recipe_id) REFERENCES recipes (id);

-- changeset andrew:1769504477943-42
ALTER TABLE recipes
    ADD CONSTRAINT FK_RECIPES_ON_AUTHOR FOREIGN KEY (author_id) REFERENCES users (id);

-- changeset andrew:1769504477943-43
ALTER TABLE recipes
    ADD CONSTRAINT FK_RECIPES_ON_NATIONAL_CUISINE FOREIGN KEY (national_cuisine_id) REFERENCES national_cuisine (id);

-- changeset andrew:1769504477943-44
ALTER TABLE recipe_categories
    ADD CONSTRAINT FK_RECIPE_CATEGORIES_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES categories (id);

-- changeset andrew:1769504477943-45
ALTER TABLE recipe_categories
    ADD CONSTRAINT FK_RECIPE_CATEGORIES_ON_RECIPE FOREIGN KEY (recipe_id) REFERENCES recipes (id);

-- changeset andrew:1769504477943-46
ALTER TABLE recipe_ingredients
    ADD CONSTRAINT FK_RECIPE_INGREDIENTS_ON_INGREDIENT FOREIGN KEY (ingredient_id) REFERENCES ingredients (id);

-- changeset andrew:1769504477943-47
ALTER TABLE recipe_ingredients
    ADD CONSTRAINT FK_RECIPE_INGREDIENTS_ON_RECIPE FOREIGN KEY (recipe_id) REFERENCES recipes (id);

-- changeset andrew:1769504477943-48
ALTER TABLE recipe_ingredients
    ADD CONSTRAINT FK_RECIPE_INGREDIENTS_ON_UNIT FOREIGN KEY (unit_id) REFERENCES units (id);

-- changeset andrew:1769504477943-49
ALTER TABLE reviews
    ADD CONSTRAINT FK_REVIEWS_ON_AUTHOR FOREIGN KEY (author_id) REFERENCES users (id);

-- changeset andrew:1769504477943-50
ALTER TABLE reviews
    ADD CONSTRAINT FK_REVIEWS_ON_RECIPE FOREIGN KEY (recipe_id) REFERENCES recipes (id);

-- changeset andrew:1769504477943-51
ALTER TABLE users
    ADD CONSTRAINT FK_USERS_ON_ROLE FOREIGN KEY (role_id) REFERENCES roles (id);

