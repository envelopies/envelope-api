ALTER TABLE items
    ADD user_id BIGINT;

ALTER TABLE users
    ADD username VARCHAR(255);

ALTER TABLE items
    ADD CONSTRAINT fk_items_on_user FOREIGN KEY (user_id) REFERENCES users (id);
