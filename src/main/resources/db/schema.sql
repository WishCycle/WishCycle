DROP SCHEMA IF EXISTS wish_cycle;
DROP TABLE IF EXISTS wish_user;
DROP TABLE IF EXISTS wish;
DROP TABLE IF EXISTS wish_list;
DROP TABLE IF EXISTS wish_list_item;
CREATE SCHEMA wish_cycle;
USE wish_cycle;

CREATE TABLE IF NOT EXISTS wish_user(
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(60) NOT NULL,
    user_password VARCHAR(60) NOT NULL,
    user_email VARCHAR(100) NOT NULL
    );

CREATE TABLE IF NOT EXISTS wish(
    wish_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    wish_name VARCHAR(60) NOT NULL,
    wish_url VARCHAR(500) NOT NULL,
    wish_price BIGINT NOT NULL
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
    wish_id BIGINT NOT NULL PRIMARY KEY,
    wish_description VARCHAR(500),
    CONSTRAINT fk_wishid
    FOREIGN KEY (wish_id)
    REFERENCES wish(wish_id)
    );
