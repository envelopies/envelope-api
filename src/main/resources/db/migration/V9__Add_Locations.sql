CREATE TABLE items_delivery_addresses
(
    item_id               UUID NOT NULL,
    delivery_addresses_id UUID NOT NULL,
    CONSTRAINT pk_items_deliveryaddresses PRIMARY KEY (item_id, delivery_addresses_id)
);

CREATE TABLE locations
(
    id        UUID NOT NULL,
    title     VARCHAR(255) NOT NULL,
    removed   BOOLEAN NOT NULL DEFAULT false,
    latitude  DECIMAL,
    longitude DECIMAL,
    CONSTRAINT pk_locations PRIMARY KEY (id)
);

ALTER TABLE items_delivery_addresses
    ADD CONSTRAINT fk_itedeladd_on_item FOREIGN KEY (item_id) REFERENCES items (id);

ALTER TABLE items_delivery_addresses
    ADD CONSTRAINT fk_itedeladd_on_location FOREIGN KEY (delivery_addresses_id) REFERENCES locations (id);