--liquibase formatted sql
--changeset kaaang:1

CREATE TABLE users
(
    id uuid NOT NULL
);
