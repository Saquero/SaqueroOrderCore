CREATE TABLE customers (
    id          UUID PRIMARY KEY,
    name        VARCHAR(150)        NOT NULL,
    email       VARCHAR(255)        NOT NULL UNIQUE,
    created_at  TIMESTAMP           NOT NULL DEFAULT now()
);

CREATE TABLE orders (
    id              UUID PRIMARY KEY,
    customer_id     UUID            NOT NULL REFERENCES customers(id),
    status          VARCHAR(50)     NOT NULL,
    total_amount    NUMERIC(19, 4)  NOT NULL,
    currency        VARCHAR(3)      NOT NULL,
    created_at      TIMESTAMP       NOT NULL DEFAULT now(),
    updated_at      TIMESTAMP       NOT NULL DEFAULT now()
);

CREATE TABLE payments (
    id              UUID PRIMARY KEY,
    order_id        UUID            NOT NULL REFERENCES orders(id),
    status          VARCHAR(50)     NOT NULL,
    amount          NUMERIC(19, 4)  NOT NULL,
    currency        VARCHAR(3)      NOT NULL,
    processed_at    TIMESTAMP,
    created_at      TIMESTAMP       NOT NULL DEFAULT now()
);

CREATE INDEX idx_orders_customer_id ON orders(customer_id);
CREATE INDEX idx_orders_status      ON orders(status);
CREATE INDEX idx_payments_order_id  ON payments(order_id);
