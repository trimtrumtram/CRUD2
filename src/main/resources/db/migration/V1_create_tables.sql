CREATE TABLE employee (
                       id BIGSERIAL PRIMARY KEY,
                       first_name VARCHAR(255) NOT NULL,
                       last_name VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL UNIQUE ,
                       password VARCHAR(255) NOT NULL,
                       role VARCHAR(50) NOT NULL
);


CREATE TABLE client (
                        id BIGSERIAL PRIMARY KEY,
                        first_name VARCHAR(255) NOT NULL,
                        last_name VARCHAR(255) NOT NULL,
                        email VARCHAR(255) NOT NULL UNIQUE,
                        phone VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE orders (
                        id BIGSERIAL PRIMARY KEY,
                        client_id BIGINT NOT NULL,

                        CONSTRAINT fk_client
                            FOREIGN KEY (client_id) REFERENCES client(id)
                            ON DELETE CASCADE
);

CREATE TABLE product (
                         id BIGSERIAL PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         description TEXT NOT NULL,
                         price NUMERIC(10, 2) NOT NULL
);

CREATE TABLE order_products (
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,

    PRIMARY KEY (order_id, product_id),
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE
);

