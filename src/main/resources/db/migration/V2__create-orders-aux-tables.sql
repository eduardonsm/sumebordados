CREATE TABLE ORDER_COLORS(
    order_id INT,
    color VARCHAR(255) NOT NULL,
    CONSTRAINT PK_ORDER_COLORS PRIMARY KEY (order_id, color)
);

CREATE TABLE ORDER_SIZES(
    order_id INT NOT NULL,
    base_size VARCHAR(20) NOT NULL,
    variant VARCHAR(20) NOT NULL,
    quantity INT
);
