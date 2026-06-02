CREATE TABLE islands (
    id          SERIAL      PRIMARY KEY,
    disposition VARCHAR(20) NOT NULL, -- 2:PAIRED, 3:TRIANGULAR, 4:SQUARED, 6:RECTANGULAR, 8: CIRCULAR
    description TEXT        NOT NULL,
    created_at  TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE workstations (
    id          SERIAL      PRIMARY KEY,
    island_id   INTEGER     NOT NULL REFERENCES island(id),
    user_id     INTEGER         NULL REFERENCES users(id),
    specs       TEXT        NOT NULL,
    created_at  TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP       NULL DEFAULT CURRENT_TIMESTAMP
);