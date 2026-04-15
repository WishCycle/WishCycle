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
    wishlist_id BIGINT NOT NULL PRIMARY KEY,
    wishlist_name VARCHAR(60) NOT NULL,
    wishlist_desc VARCHAR(500),
    user_id BIGINT NOT NULL,
    CONSTRAINT fk_userid
    FOREIGN KEY (user_id)
    REFERENCES wish_user(user_id)
    );

CREATE TABLE IF NOT EXISTS wish_list_item(
    wishlist_id BIGINT NOT NULL,
    item_id BIGINT NOT NULL PRIMARY KEY,
    wish_description VARCHAR(500),
    CONSTRAINT fk_wishid
    FOREIGN KEY (item_id)
    REFERENCES item(item_id),
    CONSTRAINT fk_wishlistid
    FOREIGN KEY (wishlist_id)
    REFERENCES wish_list(wishlist_id)
    );

