--liquibase formatted sql

--changeset skanash:1
--name: Create animal name index
--description: This change adds an index on the 'name' column of the 'animal' table

CREATE TABLE IF NOT EXISTS animal (
id INT PRIMARY KEY,
name VARCHAR(255) NOT NULL);

CREATE INDEX idx_animal_name ON animal(name)

CREATE TABLE IF NOT EXISTS app_user(
    id INT PRIMARY KEY,
    name VARCHAR (255) NOT NULL

);

--changeset skanash:2
CREATE INDEX idx_user_name ON app_user (name);
