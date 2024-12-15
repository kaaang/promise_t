-- COURSE
INSERT INTO public.course_bcs_courses (id, created_by, title, description, created_at, updated_at, deleted_at) VALUES ('e575f1a4-3ddb-4deb-b06b-abb88a2aba39', '83b80724-cd72-48e7-ae3d-689bc469d2f8', 'test', 'tttteee', '2024-12-10 19:23:23.055438', '2024-12-10 19:23:23.055438', null);

-- COURSE_TIMES
INSERT INTO public.course_bcs_course_times (id, course_id, start_time, end_time, max_capacity, created_by, created_at, updated_at, deleted_at) VALUES ('220879d2-8b38-4965-922a-d04276c7d6d8', 'e575f1a4-3ddb-4deb-b06b-abb88a2aba39', '2024-12-10 19:30:00.000000', '2024-12-10 20:00:00.000000', 5, '621c09cd-40ba-44b5-b30d-9b3b659a099b', '2024-12-15 17:03:28.249078', '2024-12-15 17:03:28.249078', null);
INSERT INTO public.course_bcs_course_times (id, course_id, start_time, end_time, max_capacity, created_by, created_at, updated_at, deleted_at) VALUES ('220879d2-8b38-4965-922a-d04276c7d6d9', 'e575f1a4-3ddb-4deb-b06b-abb88a2aba39', '2024-12-10 20:00:00.000000', '2024-12-10 20:30:00.000000', 5, '621c09cd-40ba-44b5-b30d-9b3b659a099b', '2024-12-15 17:03:28.249078', '2024-12-15 17:03:28.249078', null);

