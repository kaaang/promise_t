-- COURSE
INSERT INTO public.course_bcs_courses (id, teacher_id, title, description, created_at, updated_at, deleted_at) VALUES ('e575f1a4-3ddb-4deb-b06b-abb88a2aba39', '83b80724-cd72-48e7-ae3d-689bc469d2f8', 'test', 'tttteee', '2024-12-10 19:23:23.055438', '2024-12-10 19:23:23.055438', null);

-- COURSE_TIMES
INSERT INTO public.course_bcs_course_times (id, courses_id, start_time, end_time, max_capacity, created_at, updated_at) VALUES ('4e689a2c-d9e6-4d5c-b54c-781ec9aa3ec9', 'e575f1a4-3ddb-4deb-b06b-abb88a2aba39', '2024-12-10 19:00:00.000000', '2024-12-10 19:30:00.000000', 5, '2024-12-10 20:09:28.356189', '2024-12-10 20:09:28.356189');
INSERT INTO public.course_bcs_course_times (id, courses_id, start_time, end_time, max_capacity, created_at, updated_at) VALUES ('794f0496-a660-4cd7-97f9-662e7d4295dc', 'e575f1a4-3ddb-4deb-b06b-abb88a2aba39', '2024-12-10 19:30:00.000000', '2024-12-10 19:40:00.000000', 5, '2024-12-10 21:05:18.371849', '2024-12-10 21:05:18.371849');
