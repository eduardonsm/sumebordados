CREATE TABLE ORDER_COLORS(
    order_id INT,
    color VARCHAR(255) NOT NULL,
    CONSTRAINT PK_ORDER_COLORS PRIMARY KEY (order_id, color)
);

CREATE TYPE base_size_type AS ENUM ('P', 'M', 'G', 'GG', 'XG', 'INF_02', 'INF_04', 'INF_06', 'INF_08', 'INF_10', 'INF_12', 'INF_14');
CREATE TYPE variant_type AS ENUM ('Normal', 'Babylook', 'Manga Longa');

CREATE TABLE ORDER_SIZES(
    order_id INT NOT NULL,
    base_size base_size_type NOT NULL,
    variant variant_type NOT NULL,
    quantity INT
);
