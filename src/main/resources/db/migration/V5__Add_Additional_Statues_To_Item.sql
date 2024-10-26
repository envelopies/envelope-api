ALTER TABLE items
    ADD published BOOLEAN DEFAULT false;

ALTER TABLE items
    ADD removed BOOLEAN DEFAULT false;

ALTER TABLE items
    ALTER COLUMN published
SET NOT NULL;

ALTER TABLE items
    ALTER COLUMN removed
SET NOT NULL;
