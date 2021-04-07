-------- Tables definitions --------


---- Games table definition ----
DROP TABLE IF EXISTS games CASCADE;

CREATE TABLE games
(
    id           INT     NOT NULL GENERATED ALWAYS AS IDENTITY,
    name         varchar NOT NULL,
    publisher    varchar NOT NULL,
    developer    varchar NOT NULL,
    release_date DATE    NOT NULL,
    price        FLOAT   NOT NULL,
    stock        INT     NOT NULL,
    condition    varchar NOT NULL,
    PRIMARY KEY (id)
);


---- Categories table definition ----
DROP TABLE IF EXISTS categories CASCADE;

CREATE TABLE categories
(
    id   INT         NOT NULL GENERATED ALWAYS AS IDENTITY,
    name varchar(40) NOT NULL,
    PRIMARY KEY (id)
);


---- Categories members table (i.e relationship between games and categories) definition ----
DROP TABLE IF EXISTS category_members CASCADE;

CREATE TABLE category_members
(
    game_id     INT NOT NULL,
    category_id INT NOT NULL,
    PRIMARY KEY (game_id, category_id),
    FOREIGN KEY (game_id) REFERENCES games (id),
    FOREIGN KEY (category_id) REFERENCES categories (id)
);


---- Users table definition ----
DROP TABLE IF EXISTS users CASCADE;

CREATE TABLE users
(
    username   VARCHAR NOT NULL UNIQUE,
    password   VARCHAR NOT NULL,
    email      VARCHAR NOT NULL,
    first_name VARCHAR NOT NULL,
    last_name  VARCHAR NOT NULL,
    age        INT,
    address    VARCHAR NOT NULL,
    is_admin   BOOLEAN,
    balance    FLOAT DEFAULT 0,
    PRIMARY KEY (username)
);


---- Users Sessions table definition ----
DROP TABLE IF EXISTS users_sessions CASCADE;

CREATE TABLE users_sessions
(
    username        VARCHAR(40) NOT NULL UNIQUE,
    token           VARCHAR(40) NOT NULL,
    expiration_date TIMESTAMP   NOT NULL,

    FOREIGN KEY (username) REFERENCES users (username),
    PRIMARY KEY (username)
);


---- Coupons table definition
DROP TABLE IF EXISTS coupons;

CREATE TABLE coupons
(
    code    VARCHAR NOT NULL UNIQUE,
    value   FLOAT   NOT NULL,
    is_used BOOLEAN NOT NULL DEFAULT FALSE,

    PRIMARY KEY (code)
);

---- Purchases table definition
DROP TABLE IF EXISTS purchases CASCADE;

CREATE TABLE purchases
(
    id       INT       NOT NULL GENERATED ALWAYS AS IDENTITY UNIQUE,
    username VARCHAR   NOT NULL,
    date     TIMESTAMP NOT NULL,
    address  VARCHAR   NOT NULL,
    PRIMARY KEY (id)
);

---- Purchases Games table definition

DROP TABLE IF EXISTS purchases_games;

CREATE TABLE purchases_games
(
    purchase_id INT NOT NULL,
    game_id     INT NOT NULL,

    PRIMARY KEY (game_id, purchase_id),
    FOREIGN KEY (purchase_id) REFERENCES purchases (id),
    FOREIGN KEY (game_id) REFERENCES games (id)
);

-------- Tables data populations --------

---- Games table population ----
INSERT INTO categories (name)
VALUES ('First Person Shooter'),
       ('Adventure'),
       ('Action'),
       ('Strategy');

---- Games table population ----
INSERT INTO games (name, publisher, developer, release_date, price, stock, condition)
VALUES ('Day of the Tentacle', 'Lucas Arts', 'Double Fine', '1993-06-25', 25.0, 30, 'BRAND_NEW'),
       ('Team Fortress 2', 'Valve', 'Valve', '2007-10-10', 7, 20, 'LIKE_NEW'),
       ('Counter Strike: Global Offensive', 'Valve', 'Valve', '2012-08-21', 15.0, 40, 'BRAND_NEW'),
       ('Bloons TD 6', 'Ninja Kiwi', 'Ninja Kiwi', '2018-12-18', 15, 30, 'LIKE_NEW'),
       ('Apex Legends', 'Electronic Arts', 'Respawn Entertainment', '2020-11-05', 5, 20, 'LIKE_NEW'),
       ('Among Us', 'Innersloth', 'Innersloth', '2018-11-16', 30, 15, 'LIKE_NEW'),
       ('Catan Universe', 'United Soft Media', 'Exozet', '2017-04-25', 30, 15, 'LIKE_NEW'),
       ('Sid Meier''s Civilization VI', '2K', 'Fireaxis Games', '2016-10-21', 25, 15, 'BRAND_NEW'),
       ('Cluedo', 'Marmalade Games', 'Marmalade Games', '2018-05-17', 20, 20, 'LIKE_NEW'),
       ('FarCry 5', 'Ubisoft', 'Ubisoft', '2018-03-27', 20, 60, 'BRAND_NEW'),
       ('Borderlands 3', '2K', 'Gearbox', '2020-05-13', 30, 45, 'BRAND_NEW');

---- Categories Members table population ----
INSERT INTO category_members (game_id, category_id)
VALUES (1, 2),
       (1, 4),
       (2, 1),
       (2, 3),
       (2, 4),
       (3, 1),
       (3, 2),
       (4, 4),
       (5, 1),
       (6, 3),
       (6, 4),
       (7, 4),
       (8, 4),
       (9, 4),
       (10, 1),
       (10, 3),
       (11, 3),
       (11, 4);

---- Users table population ----
INSERT INTO users (username, password, email, first_name, last_name, address, is_admin, balance)
values ('dvirdov', 'password', 'dvirdove@gmail.com', 'Dvir', 'Dov', 'Dvir Address', FALSE, 1000),
       ('yohaimazuz', 'password', 'yohai7700@gmail.com', 'Yohai', 'Mazuz', 'Yohai Address', TRUE, 0),
       ('ofekmarks', 'password', 'ofekmarks@gmail.com', 'Ofek', 'Marks', 'Ofek Address', TRUE, 0);

---- Coupons table population ----
INSERT INTO coupons (code, value)
values ('code1', 150),
       ('code2', 200);

---- Purchases table population ----
INSERT INTO purchases (username, date, address)
VALUES ('yohaimazuz', '2020-08-10', NULL);
