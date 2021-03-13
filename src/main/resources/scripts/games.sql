DROP TABLE games;

CREATE TABLE games (
   id INT NOT NULL GENERATED ALWAYS AS IDENTITY,
   name varchar (40) NOT NULL,
    publisher varchar(20) NOT NULL,
    developer varchar(20) NOT NULL,
    release_date DATE NOT NULL,
    price FLOAT NOT NULL,
    stock INT NOT NULL,
    condition varchar(10) NOT NULL,
   PRIMARY KEY (id)
);

INSERT INTO games (name, publisher, developer, release_date, price, stock, condition)
VALUES
    ('Day of the Tentacle', 'Lucas Arts', 'Double Fine', '1993-06-25', 25.0, 30, 'BRAND_NEW'),
    ('Team Fortress 2', 'Valve', 'Valve', '2007-10-10', 0, 20, 'LIKE_NEW'),
    ('Counter Strike: Global Offensive', 'Valve', 'Valve', '2012-08-21', 15.0, 40, 'BRAND_NEW');
