DROP TABLE IF EXISTS books;

CREATE TABLE books
(
    id     INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    title  VARCHAR(250) NOT NULL,
    author VARCHAR(250) NOT NULL,
    year   INTEGER DEFAULT NULL
);

INSERT INTO books (title, author, year)
VALUES ('Clean Code', 'Robert Martin', 2008),
       ('The Clean Coder', 'Robert Martin', 2011),
       ('Clean Architecture', 'Robert Martin', 2017),
       ('Test Driven Development by Example', 'Kent Beck', 2003),
       ('Working Effectively With Legacy Code', 'Michael C. Feathers', 2001);