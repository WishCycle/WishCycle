-- SCHEMAS
DROP SCHEMA IF EXISTS wish_cycle;
CREATE SCHEMA wish_cycle;
USE wish_cycle;

CREATE TABLE IF NOT EXISTS wish_user(
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(60) NOT NULL UNIQUE,
    user_password VARCHAR(60) NOT NULL,
    user_email VARCHAR(100) NOT NULL UNIQUE
    );

CREATE TABLE IF NOT EXISTS item(
    item_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    item_name VARCHAR(60) NOT NULL,
    item_url VARCHAR(500) NOT NULL,
    item_price BIGINT NOT NULL
    );

CREATE TABLE IF NOT EXISTS wish_list(
    wishlist_id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    wishlist_name VARCHAR(200) NOT NULL,
    wishlist_desc VARCHAR(999),
    user_id BIGINT NOT NULL,
    CONSTRAINT fk_userid
    FOREIGN KEY (user_id)
    REFERENCES wish_user(user_id)
    ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS wish_list_item(
    wishlist_id BIGINT NOT NULL,
    item_id BIGINT NOT NULL PRIMARY KEY,
    wish_description VARCHAR(500),
    CONSTRAINT fk_wishid
    FOREIGN KEY (item_id)
    REFERENCES item(item_id)
    ON DELETE CASCADE,
    CONSTRAINT fk_wishlistid
    FOREIGN KEY (wishlist_id)
    REFERENCES wish_list(wishlist_id)
    ON DELETE CASCADE
    );


-- DATA
INSERT INTO wish_user (username, user_password, user_email) VALUES
                             ('simonBeCh', '112pizza', 'sich0008@stud.ek.dk'),
                             ('mowgli-dk', '1234', 'eman1000@stud.ek.dk'),
                             ('slikmonsteret', 'jokke700', 'joes0001@stud.ek.dk');

INSERT INTO item (item_name, item_url, item_price) VALUES
                             ('Calvin Klein boxer shorts', 'https://www.amazon.de/dp/B001BEAWZM', 243),
                             ('Algebra (Graduate Texts in Mathematics)', 'https://www.amazon.de/dp/038795385X', 420),
                             ('Titleist Pro V1', 'https://www.amazon.de/dp/B0DPN7QZ9R', 479),
                             ('Vibox Gaming PC - RTX 5090', 'https://www.amazon.de/dp/B0DZDQWWYD', 40584),
                             ('Rolly Toys Trettraktor', 'https://www.amazon.de/dp/B08KRXS5JG', 2713),
                             ('DIRE STRAITS - LOVE OVER GOLD (Vinyl)', 'https://www.amazon.de/dp/B00KJN3KXC', 4028);
INSERT INTO wish_list (wishlist_name, wishlist_desc, user_id) VALUES
                              ('Simon''s ønskeliste', 'Simons ønskeliste for hans fødselsdag', 1),
                              ('Jokke''s mokke',  'Jokke''s liste til alt han mangler', 2),
                              ('Emils traktor liste', 'Seje traktorer', 3);

INSERT INTO wish_list_item (wishlist_id, item_id, wish_description) VALUES
                               (1, 1, 'Jeg har brug for nye underhyler'),
                               (1, 2, 'Vil gerne øve mig på algebra'),
                               (2, 3, 'NYE GOLFBOLDE!!!!!'),
                               (2, 4, 'Så skal der games'),
                               (3, 5, 'Jeg har altid ønsket mig en traktor'),
                               (2, 6, 'Jeg skal da ha den');