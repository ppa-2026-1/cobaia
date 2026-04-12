INSERT INTO roles (name) VALUES
    ('ROLE_USER'),
    ('ROLE_GUEST'),
    ('ROLE_VIEWER')
;

INSERT INTO users (handle, email, password, created_at)
VALUES 
    ('marcio', 'marcio@mail.com', 'password', CURRENT_TIMESTAMP),
    ('josue', 'josue@mail.com', 'password', CURRENT_TIMESTAMP)
;

INSERT INTO users_roles (user_id, role_id) 
VALUES
    (1, 1), -- Marcio has ROLE_USER
    (1, 3), -- Marcio has ROLE_VIEWER
    (2, 2)  -- Josue has ROLE_GUEST
;

INSERT INTO profiles (id, name, company, type)
VALUES
    (1, 'Marcio Ramos', 'Empresa 1', 'PROFESSIONAL'),
    (2, 'Josue Torres', 'Empresa 2', 'FREE')
;

-- RELACIONAL
INSERT INTO vulnerability_reports (system_under_test, created_at, updated_at, user_id) 
VALUES
    ('System A', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
    ('System B', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
    ('System C', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2)
;

INSERT INTO vulnerabilities (description, severity, report_id, created_at, updated_at)
VALUES
    ('SQL Injection vulnerability in login form', 'HIGH', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Cross-Site Scripting (XSS) in user profile page', 'MEDIUM', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('Insecure Direct Object Reference (IDOR) in file download feature', 'HIGH', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
;