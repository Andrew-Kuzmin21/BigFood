-- liquibase formatted sql

-- changeset andrew:1770298753190-14
ALTER TABLE media DROP CONSTRAINT fk_media_on_media_type;

-- changeset andrew:1770298753190-3
CREATE SEQUENCE IF NOT EXISTS media_type_id_seq START WITH 1 INCREMENT BY 1;

-- changeset andrew:1770298753190-6
CREATE TABLE media_types
(
    id              BIGINT       NOT NULL,
    media_type_name VARCHAR(255) NOT NULL,
    CONSTRAINT pk_media_types PRIMARY KEY (id)
);

-- changeset andrew:1770298753190-9
ALTER TABLE media_types
    ADD CONSTRAINT uc_media_types_media_type_name UNIQUE (media_type_name);

-- changeset andrew:1770298753190-11
ALTER TABLE media
    ADD CONSTRAINT FK_MEDIA_ON_MEDIA_TYPE FOREIGN KEY (media_type_id) REFERENCES media_types (id);

-- changeset andrew:1770298753190-16
DROP TABLE types CASCADE;

-- changeset andrew:1770298753190-17
DROP SEQUENCE type_id_seq CASCADE;

