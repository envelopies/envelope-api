CREATE TABLE categories
(
    id                 UUID         NOT NULL,
    title              VARCHAR(255) NOT NULL,
    removed            BOOLEAN      NOT NULL DEFAULT false,
    parent_category_id UUID,
    CONSTRAINT pk_categories PRIMARY KEY (id)
);

ALTER TABLE items
    ADD category_id UUID;

ALTER TABLE items
    ALTER COLUMN category_id SET NOT NULL;

ALTER TABLE categories
    ADD CONSTRAINT FK_CATEGORIES_ON_PARENTCATEGORY FOREIGN KEY (parent_category_id) REFERENCES categories (id);

ALTER TABLE items
    ADD CONSTRAINT FK_ITEMS_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES categories (id);