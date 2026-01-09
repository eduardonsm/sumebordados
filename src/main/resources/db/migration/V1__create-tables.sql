
CREATE TABLE EMPLOYEES(
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    username VARCHAR(20) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'EMPLOYEE'
);
CREATE TABLE CUSTOMERS(
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    phone VARCHAR(18) UNIQUE NOT NULL,
    address TEXT NOT NULL
);
CREATE TABLE ORDERS(
    id SERIAL PRIMARY KEY,
    customer_id INT,
    model VARCHAR(50) NOT NULL,
    fabric VARCHAR(50) NOT NULL,
    has_cut BOOLEAN NOT NULL,
    quantity INT NOT NULL,
    chest_customization INT DEFAULT 0,
    back_customization INT DEFAULT 0,
    sleeve_customization INT DEFAULT 0,
    unit_price DECIMAL(10,2) NOT NULL,
    total_price DECIMAL(10,2) NOT NULL,
    delivery_date DATE,
    advance_date DATE,
    advance_amount DECIMAL(10,2) DEFAULT 0,
    remaining_amount DECIMAL(10,2) DEFAULT 0,
    status VARCHAR(50) NOT NULL,
    artwork_url TEXT
);
