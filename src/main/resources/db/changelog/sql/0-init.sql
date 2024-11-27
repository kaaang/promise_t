--liquibase formatted sql
--changeset kaaang:0

CREATE TABLE IF NOT EXISTS users
(
    id         uuid         not null primary key,
    email      varchar(90),
    name       varchar(20),
    password   varchar(255) not null,
    role_type  varchar(30)  not null,
    created_at timestamp,
    updated_at timestamp,
    deleted_at timestamp
);
