-- Create users table
CREATE TABLE IF NOT EXISTS users
(
    id           BINARY(16) PRIMARY KEY,
    username     VARCHAR(255) NOT NULL,
    email        VARCHAR(255) NOT NULL,
    access_token BINARY(16)   NOT NULL
);

-- Create monitored_endpoints table
CREATE TABLE IF NOT EXISTS monitored_endpoints
(
    id                  BINARY(16) PRIMARY KEY,
    name                VARCHAR(255) NOT NULL,
    url                 VARCHAR(255) NOT NULL,
    date_of_last_update DATETIME     NOT NULL,
    date_of_last_check  DATETIME,
    monitored_interval  INT          NOT NULL,
    owner_id            BINARY(16)   NOT NULL,
    FOREIGN KEY (owner_id) REFERENCES users (id) ON DELETE CASCADE
);

-- Create monitoring_results table
CREATE TABLE IF NOT EXISTS monitoring_results
(
    id                    BINARY(16) PRIMARY KEY,
    date_of_check         DATETIME   NOT NULL,
    http_status_code      INT        NOT NULL,
    payload               TEXT,
    monitored_endpoint_id BINARY(16) NOT NULL,
    FOREIGN KEY (monitored_endpoint_id) REFERENCES monitored_endpoints (id) ON DELETE CASCADE
);

-- Insert test users
INSERT INTO users (id, username, email, access_token)
SELECT
    UNHEX(REPLACE('11111111-1111-1111-1111-111111111111', '-', '')), 'user1', 'user1@example.com',
    UNHEX(REPLACE('22222222-2222-2222-2222-222222222222', '-', ''))

FROM DUAL
WHERE NOT EXISTS (
    SELECT 1 FROM users WHERE username = 'user1' OR email = 'user1@example.com'
);

INSERT INTO users (id, username, email, access_token)
SELECT
    UNHEX(REPLACE('33333333-3333-3333-3333-333333333333', '-', '')), 'user2', 'user2@example.com',
    UNHEX(REPLACE('44444444-4444-4444-4444-444444444444', '-', ''))

FROM DUAL
WHERE NOT EXISTS (
    SELECT 1 FROM users WHERE username = 'user2' OR email = 'user2@example.com'
);

INSERT INTO users (id, username, email, access_token)
SELECT
    UNHEX(REPLACE('55555555-5555-5555-5555-555555555555', '-', '')), 'user3', 'user3@example.com',
    UNHEX(REPLACE('66666666-6666-6666-6666-666666666666', '-', ''))

FROM DUAL
WHERE NOT EXISTS (
    SELECT 1 FROM users WHERE username = 'user3' OR email = 'user3@example.com'
);


