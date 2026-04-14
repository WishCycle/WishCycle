DROP SCHEMA IF EXISTS wish_cycle;
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
    wish_link VARCHAR(500) NOT NULL,
    wish_description VARCHAR(500)
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