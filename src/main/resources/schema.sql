create table if not exists  product (
    id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    category VARCHAR(255),
    price DECIMAL(10,2),
    quantity LONG
    -- on update restrict
);
