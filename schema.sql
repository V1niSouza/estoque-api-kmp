CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE products (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    description TEXT,
    sku VARCHAR(100) NOT NULL UNIQUE,
    category VARCHAR(150),
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE stock_items (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    product_id UUID NOT NULL,
    quantity INTEGER NOT NULL DEFAULT 0,
    unit_price DECIMAL(10,2) NOT NULL,
    location VARCHAR(255),
    updated_at TIMESTAMP DEFAULT NOW(),

    CONSTRAINT fk_product
        FOREIGN KEY (product_id)
        REFERENCES products(id)
        ON DELETE CASCADE
);


INSERT INTO products (name, description, sku, category)
VALUES
('Caneta Azul', 'Caneta esferográfica azul 0.7mm', 'CAN-001', 'Papelaria'),
('Caderno A4', 'Caderno universitário 200 folhas', 'CAD-002', 'Papelaria'),
('Mouse Gamer', 'Mouse com LED e 7200 DPI', 'MOU-003', 'Eletrônicos'),
('Teclado Mecânico', 'Teclado switch blue ABNT2', 'TEC-004', 'Eletrônicos'),
('Garrafas Térmicas', 'Garrafa inox 500ml', 'GAR-005', 'Utilidades');

SELECT * FROM products;

INSERT INTO stock_items (product_id, quantity, unit_price, location)
VALUES
('411cab11-cff3-4b89-89a0-d1239ced6f4b', 100, 2.50, 'A1'),
('411cab11-cff3-4b89-89a0-d1239ced6f4b', 50, 2.50, 'A2'),
('76227fb4-0a65-4952-a512-8b972362d2f0', 30, 15.90, 'B1'),
('76227fb4-0a65-4952-a512-8b972362d2f0', 20, 15.90, 'B2'),
('abbd5634-225c-4e92-99a8-3c91c6b9fe7d', 15, 89.90, 'C1'),
('64c07087-e603-461b-8bee-42f184c2b7d4', 10, 199.90, 'C2'),
('62c0f5e6-3334-4451-bb6f-597df70a3732', 40, 25.00, 'D1'),
('62c0f5e6-3334-4451-bb6f-597df70a3732', 60, 25.00, 'D2');


create or replace function get_stock_summary()
returns table (
    product_id uuid,
    product_name text,
    total_quantity bigint
)
language sql
as $$
    select
        p.id as product_id,
        p.name as product_name,
        sum(s.quantity) as total_quantity
    from stock_items s
    join products p on p.id = s.product_id
    group by p.id, p.name;
$$;

create policy "Allow select stock"
on stock_items
for select
using (true);


CREATE POLICY "Allow read products"
ON products
FOR SELECT
USING (true);

create policy "allow insert stock"
on stock_items
for insert
with check (true);

create policy "allow select stock"
on stock_items
for select
using (true);

create policy "allow update stock"
on stock_items
for update
using (true);

create policy "allow delete stock"
on stock_items
for delete
using (true);

