-- data.sql
CREATE TABLE IF NOT EXISTS my_table (
    id INT PRIMARY KEY,
    name VARCHAR(255)
);

INSERT INTO my_table (id, name) VALUES (1, 'Test Name');
