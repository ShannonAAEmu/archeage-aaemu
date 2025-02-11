DROP TABLE IF EXISTS accounts;
CREATE TABLE accounts
(
    id         BIGSERIAL NOT NULL PRIMARY KEY,
    name       TEXT      NOT NULL,
    password   TEXT      NOT NULL,
    last_login TIMESTAMP,
    update_at  TIMESTAMP          DEFAULT NULL,
    create_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
comment on column accounts.id is 'Account ID';
comment on column accounts.name is 'Account name';
comment on column accounts.password is 'Account password';
comment on column accounts.last_login is 'Account last login date';
comment on column accounts.update_at is 'Account updated date';
comment on column accounts.create_at is 'Account created date';
