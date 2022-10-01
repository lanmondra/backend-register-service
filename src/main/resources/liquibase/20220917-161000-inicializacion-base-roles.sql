--liquibase formatted sql

-- changeset angel:20220917-161000-1


-- Inicialitzacio de taules
INSERT INTO base_role (id, name) VALUES
(1, 'ROLE_ADMIN'),
(2, 'ROLE_USER'),
(3, 'ROLE_CLIENT');
