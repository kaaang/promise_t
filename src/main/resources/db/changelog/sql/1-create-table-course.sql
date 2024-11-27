--liquibase formatted sql
--changeset kaaang:1

CREATE TABLE IF NOT EXISTS courses
(
    id          uuid not null primary key,
    teacher_id  uuid not null,
    title       varchar(50),
    description TEXT,
    created_at timestamp,
    updated_at timestamp,
    deleted_at timestamp
);
