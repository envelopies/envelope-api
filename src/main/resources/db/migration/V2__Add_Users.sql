CREATE TABLE users
(
    id       BIGINT  NOT NULL,
    verified BOOLEAN NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

CREATE TABLE authorities
(
    user_id   BIGINT       NOT NULL,
    authority VARCHAR(255) NOT NULL
);

ALTER TABLE authorities
    ADD CONSTRAINT fk_authorities_on_users FOREIGN KEY (user_id) REFERENCES users (id);
