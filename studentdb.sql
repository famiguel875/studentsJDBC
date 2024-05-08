CREATE DATABASE studentdb;
CREATE USER 'studentuser'@'localhost' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON studentdb.* TO 'studentuser'@'localhost';
FLUSH PRIVILEGES;

USE studentdb;
CREATE TABLE students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(10) NOT NULL
);

USE studentdb;

INSERT INTO students (name) VALUES ('Ana');
INSERT INTO students (name) VALUES ('Marta');
INSERT INTO students (name) VALUES ('Sofía');
INSERT INTO students (name) VALUES ('Lucía');
INSERT INTO students (name) VALUES ('Jorge');
INSERT INTO students (name) VALUES ('Elena');