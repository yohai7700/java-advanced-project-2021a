-- Games table definition
DROP TABLE IF EXISTS games CASCADE;

CREATE TABLE games
(
    id           INT         NOT NULL GENERATED ALWAYS AS IDENTITY,
    name         varchar(40) NOT NULL,
    publisher    varchar(20) NOT NULL,
    developer    varchar(20) NOT NULL,
    release_date DATE        NOT NULL,
    price        FLOAT       NOT NULL,
    stock        INT         NOT NULL,
    condition    varchar(10) NOT NULL,
    PRIMARY KEY (id)
);
-- Games table population
INSERT INTO games (name, publisher, developer, release_date, price, stock, condition)
VALUES ('Day of the Tentacle', 'Lucas Arts', 'Double Fine', '1993-06-25', 25.0, 30, 'BRAND_NEW'),
       ('Team Fortress 2', 'Valve', 'Valve', '2007-10-10', 0, 20, 'LIKE_NEW'),
       ('Counter Strike: Global Offensive', 'Valve', 'Valve', '2012-08-21', 15.0, 40, 'BRAND_NEW');

-- Categories table definition
DROP TABLE IF EXISTS categories CASCADE;

CREATE TABLE categories
(
    id   INT         NOT NULL GENERATED ALWAYS AS IDENTITY,
    name varchar(40) NOT NULL,
    PRIMARY KEY (id)
);

-- Categories members table (i.e relationship between games and categories) definition
DROP TABLE IF EXISTS category_members CASCADE;

CREATE TABLE category_members
(
    game_id     INT NOT NULL,
    category_id INT NOT NULL,
    PRIMARY KEY (game_id, category_id),
    FOREIGN KEY (game_id) REFERENCES games (id),
    FOREIGN KEY (category_id) REFERENCES categories (id)
);

-- Users table definition
DROP TABLE IF EXISTS users CASCADE;

CREATE TABLE users
(
    username   VARCHAR(40)  NOT NULL UNIQUE,
    password   VARCHAR(100) NOT NULL,
    first_name VARCHAR(20),
    last_name  VARCHAR(20),
    age        INT,
    address    VARCHAR(40),
    isAdmin    BIT,
    PRIMARY KEY (username)
);