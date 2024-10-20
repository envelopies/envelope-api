CREATE TABLE items
(
    id          UUID    NOT NULL,
    title       VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    price       INTEGER NOT NULL,
    created_at  TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT pk_item PRIMARY KEY (id)
);
