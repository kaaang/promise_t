--liquibase formatted sql
--changeset kaaang:1

CREATE TABLE IF NOT EXISTS course_bcs_courses
(
    id          uuid NOT NULL PRIMARY KEY,
    created_by  uuid NOT NULL,
    title       VARCHAR(50),
    description TEXT,
    created_at  TIMESTAMP,
    updated_at  TIMESTAMP,
    deleted_at  TIMESTAMP
    );


CREATE TABLE IF NOT EXISTS course_bcs_course_times
(
    id           uuid NOT NULL PRIMARY KEY,
    course_id    uuid NOT NULL,
    start_time   TIMESTAMP,
    end_time     TIMESTAMP,
    max_capacity INT  NOT NULL,
    created_by   uuid NOT NULL,
    created_at   TIMESTAMP,
    updated_at   TIMESTAMP,
    deleted_at   TIMESTAMP,
    CONSTRAINT fk_course_times_course FOREIGN KEY (course_id) REFERENCES course_bcs_courses (id) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS course_bcs_course_time_reservations
(
    id             uuid NOT NULL PRIMARY KEY,
    course_time_id uuid NOT NULL,
    user_id        uuid NOT NULL,
    created_at     TIMESTAMP,
    updated_at     TIMESTAMP,
    CONSTRAINT fk_course_time_reservation_course_time FOREIGN KEY (course_time_id) REFERENCES course_bcs_course_times (id) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS course_bcs_course_time_comments
(
    id             uuid NOT NULL
        PRIMARY KEY,
    created_at     TIMESTAMP,
    updated_at     TIMESTAMP,
    created_by     uuid NOT NULL,
    course_time_id uuid
        CONSTRAINT fk_course_time_comment_course_time
            REFERENCES course_bcs_course_times
);