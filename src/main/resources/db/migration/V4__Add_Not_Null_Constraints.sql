ALTER TABLE items
    ALTER COLUMN user_id
SET NOT NULL;

ALTER TABLE users
    ALTER COLUMN username
SET NOT NULL;