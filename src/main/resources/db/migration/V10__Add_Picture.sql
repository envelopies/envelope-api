CREATE TABLE items_pictures
(
    item_id     UUID NOT NULL,
    pictures_id UUID NOT NULL,
    CONSTRAINT pk_items_pictures PRIMARY KEY (item_id, pictures_id)
);

CREATE TABLE pictures
(
    id    UUID         NOT NULL,
    title VARCHAR(255) NOT NULL,
    CONSTRAINT pk_pictures PRIMARY KEY (id)
);

ALTER TABLE items_pictures
    ADD CONSTRAINT fk_itepic_on_item FOREIGN KEY (item_id) REFERENCES items (id);

ALTER TABLE items_pictures
    ADD CONSTRAINT fk_itepic_on_picture FOREIGN KEY (pictures_id) REFERENCES pictures (id);