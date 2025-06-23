ALTER TABLE items
    ADD quantity INTEGER NOT NULL default 0;

ALTER TABLE items
    ADD unit VARCHAR(255) NOT NULL default 'шт';