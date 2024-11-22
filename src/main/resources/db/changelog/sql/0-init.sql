--liquibase formatted sql
--changeset kaaang:1

CREATE TABLE users
(
    id         uuid         not null primary key,
    email      varchar(90),
    username   varchar(20),
    password   varchar(255) not null,
    role_type  varchar(30)  not null,
    created_at timestamp,
    updated_at timestamp,
    deleted_at timestamp
);
