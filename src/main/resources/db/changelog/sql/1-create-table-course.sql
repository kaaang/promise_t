--liquibase formatted sql
--changeset kaaang:1

CREATE TABLE IF NOT EXISTS course_bcs_courses
(
    id          uuid not null primary key,
    created_by  uuid not null,
    title       varchar(50),
    description TEXT,
    created_at  timestamp,
    updated_at  timestamp,
    deleted_at  timestamp
    );


CREATE TABLE IF NOT EXISTS course_bcs_course_times
(
    id           uuid not null primary key,
    course_id   uuid not null,
    start_time   timestamp,
    end_time     timestamp,
    max_capacity int  not null,
    created_by   uuid not null,
    created_at   timestamp,
    updated_at   timestamp,
    deleted_at  timestamp,
    CONSTRAINT fk_course_times_course FOREIGN KEY (course_id) REFERENCES course_bcs_courses (id) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS course_bcs_course_time_reservations
(
    id              uuid not null primary key,
    course_time_id uuid not null,
    user_id         uuid not null,
    created_at      timestamp,
    updated_at      timestamp,
    CONSTRAINT fk_course_time_reservation_course_time FOREIGN KEY (course_time_id) REFERENCES course_bcs_course_times (id) ON DELETE CASCADE
    );