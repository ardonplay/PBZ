SET datestyle TO 'ISO, DMY';
CREATE TABLE "product_type"(
    "id" SERIAL PRIMARY KEY,
    "name" VARCHAR NOT NULL UNIQUE
);

CREATE TYPE "person_type" AS ENUM ( 'INDIVIDUAL', 'LEGAL' );

CREATE CAST (character varying AS public.person_type) WITH INOUT AS ASSIGNMENT;

CREATE TABLE
    "products" (
        "id" SERIAL PRIMARY KEY,
        "name" varchar NOT NULL UNIQUE,
        "type" int NOT NULL,
        FOREIGN KEY ("type") REFERENCES "product_type" ("id") ON DELETE CASCADE
    );
    

CREATE TABLE "customers" (
    "id" uuid NOT NULL DEFAULT (gen_random_uuid()) PRIMARY KEY,
    "name" varchar NOT NULL,
    "type" person_type NOT NULL,
    "adress" varchar NOT NULL,
    "phone_number" VARCHAR(11) UNIQUE NOT NULL
);

CREATE TABLE "bank_details" (
    "id" SERIAL PRIMARY KEY,
    "customer_id" uuid UNIQUE,
    "number" VARCHAR NOT NULL,
    "bank_name" varchar NOT NULL,
    FOREIGN KEY ("customer_id") REFERENCES "customers" ("id") ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE
    "destinations" (
        "id" SERIAL PRIMARY KEY,
        "name" varchar NOT NULL,
        "region" varchar NOT NULL,
        "country" varchar NOT NULL
    );

CREATE TABLE
    "waybills" (
        "id" SERIAL PRIMARY KEY,
        "customer_id" uuid NOT NULL,
        "date" date NOT NULL,
        "destination" integer NOT NULL,
        FOREIGN KEY ("customer_id") REFERENCES "customers" ("id") ON DELETE CASCADE ON UPDATE CASCADE,
        FOREIGN KEY ("destination") REFERENCES "destinations" ("id") ON DELETE CASCADE ON UPDATE CASCADE
    );

CREATE TABLE
    "waybil_products" (
        "id" SERIAL PRIMARY KEY,
        "product_id" integer NOT NULL,
        "waybil_id" integer NOT NULL,
        "quantity" integer NOT NULL,
        "price" decimal(18,2) NOT NULL,
        FOREIGN KEY ("product_id") REFERENCES "products" ("id") ON DELETE CASCADE ON UPDATE CASCADE,
        FOREIGN KEY ("waybil_id") REFERENCES "waybills" ("id") ON DELETE CASCADE ON UPDATE CASCADE
    );


INSERT INTO "product_type" ("name") VALUES
('INDUSTRIAL'),
('HOUSEHOLD'),
('COMMERCE');

INSERT INTO "products" ("name", "type") VALUES
('Холодильник ATLANT ХМ 4626-109 ND', 2),
('Ленточнопильный станок Stalex BS-85', 1),
('Компактный банкомат NCR SelfServ 22', 3),
('Блок питания Deepcool PF600 (R-PF600D-HA0B-EU) 600W, APFC, 80 Plus', 2),
('Посудомоечная машина Electrolux EEA12100L', 2);

INSERT INTO "customers" ("name", "type", "adress", "phone_number") VALUES
('Иванов Иван', 'INDIVIDUAL', 'ул. Ленина, 123', '1234567890'),
('Владимир Мощук', 'INDIVIDUAL', 'ул. Кунцевщина, 38', '298277252'),
('ЧП Сепия', 'LEGAL', 'ул. Притыцкого, 105 — 194', '296291276');

INSERT INTO "bank_details" ("customer_id", "number", "bank_name")
VALUES  ((SELECT customers.id FROM "customers" LIMIT 1 OFFSET 0), '1234567890', 'Bank of Example'),
        ((SELECT customers.id FROM "customers" LIMIT 1 OFFSET 1), '9876543210', 'Another Bank'),
        ((SELECT customers.id FROM "customers" LIMIT 1 OFFSET 2), '5555555555', 'Bank XYZ');


INSERT INTO "destinations" ("name", "region", "country") VALUES
('Минск', 'Минск', 'Беларусь'),
('Брест', 'Брестская', 'Беларусь');



INSERT INTO "waybills" ("date", "customer_id", "destination") VALUES
('27/10/2023', (SELECT customers.id FROM "customers" LIMIT 1 OFFSET 0),1),
('25/10/2023', (SELECT customers.id FROM "customers" LIMIT 1 OFFSET 1),1),
('30/08/2023',(SELECT customers.id FROM "customers" LIMIT 1 OFFSET 2),1),
('30/08/2023',(SELECT customers.id FROM "customers" LIMIT 1 OFFSET 0),1);;


INSERT INTO "waybil_products" ("product_id", "waybil_id", "quantity",
  "price") VALUES
(1, 1, 10, 1553),
(2, 2, 2, 703),
(3,3, 5, 586),
(3,4,1000, 4000);





-- LOGGING
CREATE TABLE log_table (
    id SERIAL PRIMARY KEY,
    table_name VARCHAR NOT NULL,
    action VARCHAR NOT NULL,
    row_id VARCHAR,
    timestamp TIMESTAMP NOT NULL
);

CREATE OR REPLACE FUNCTION logger()
RETURNS TRIGGER AS $$
BEGIN
    IF TG_OP = 'INSERT' THEN
        INSERT INTO log_table (table_name, action, row_id, timestamp)
        VALUES (TG_TABLE_NAME, 'INSERT', NEW.id::VARCHAR, NOW());
    ELSIF TG_OP = 'UPDATE' THEN
        INSERT INTO log_table (table_name, action, row_id, timestamp)
        VALUES (TG_TABLE_NAME, 'UPDATE', NEW.id::VARCHAR, NOW());
    ELSIF TG_OP = 'DELETE' THEN
        INSERT INTO log_table (table_name, action, row_id, timestamp)
        VALUES (TG_TABLE_NAME, 'DELETE',  OLD.id::VARCHAR, NOW());
    END IF;
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER product_type_trigger
AFTER INSERT OR UPDATE OR DELETE ON "product_type"
FOR EACH ROW EXECUTE FUNCTION logger();


CREATE TRIGGER products_trigger
AFTER INSERT OR UPDATE OR DELETE ON "products"
FOR EACH ROW EXECUTE FUNCTION logger();


CREATE TRIGGER products_trigger
AFTER INSERT OR UPDATE OR DELETE ON "customers"
FOR EACH ROW EXECUTE FUNCTION logger();

CREATE TRIGGER products_trigger
AFTER INSERT OR UPDATE OR DELETE ON "bank_details"
FOR EACH ROW EXECUTE FUNCTION logger();

CREATE TRIGGER products_trigger
AFTER INSERT OR UPDATE OR DELETE ON "destinations"
FOR EACH ROW EXECUTE FUNCTION logger();

CREATE TRIGGER products_trigger
AFTER INSERT OR UPDATE OR DELETE ON "waybills"
FOR EACH ROW EXECUTE FUNCTION logger();

CREATE TRIGGER products_trigger
AFTER INSERT OR UPDATE OR DELETE ON "waybil_products"
FOR EACH ROW EXECUTE FUNCTION logger();



CREATE OR REPLACE PROCEDURE add_product(
    IN product_name VARCHAR,
    IN product_type_name VARCHAR,
    OUT product_id INTEGER
)
LANGUAGE plpgsql
AS $$
DECLARE
    product_type_id INTEGER;
BEGIN
    -- Вставить новый тип продукта, если его еще нет
    INSERT INTO product_type(name)
    VALUES (product_type_name)
    ON CONFLICT (name) DO NOTHING;
    

    -- Получить или обновить идентификатор существующего типа продукта
    SELECT id INTO product_type_id FROM product_type WHERE name = product_type_name;

    -- Вставить новый продукт
    INSERT INTO products(name, type)
    VALUES (product_name, product_type_id)
    RETURNING id INTO product_id;

EXCEPTION
    WHEN OTHERS THEN
        RAISE;
END;
$$;


CREATE OR REPLACE PROCEDURE delete_product(
    IN product_id INTEGER
)
LANGUAGE plpgsql
AS $$
BEGIN
    -- Удалить продукт по имени
    DELETE FROM products
    WHERE id = product_id;

EXCEPTION
    WHEN OTHERS THEN
        RAISE;
END;
$$;

CREATE OR REPLACE PROCEDURE update_product(
    IN product_id INTEGER,
    IN new_product_name VARCHAR,
    IN product_type_name VARCHAR
)
LANGUAGE plpgsql
AS $$
DECLARE
    new_product_type_id INTEGER;
BEGIN

    INSERT INTO product_type(name)
    VALUES (product_type_name)
    ON CONFLICT (name) DO NOTHING;

    SELECT id INTO new_product_type_id FROM product_type WHERE name = product_type_name;

    -- Обновить информацию о продукте
    UPDATE products
    SET name = new_product_name, type = new_product_type_id
    WHERE id = product_id;

EXCEPTION
    WHEN OTHERS THEN
        RAISE;
END;
$$;




CREATE OR REPLACE PROCEDURE add_new_waybill(
    IN customer_id UUID,
    IN date_param DATE,
    IN destination_id INTEGER,
    IN products_data VARCHAR,
    OUT result_waybill_id INTEGER
)
LANGUAGE plpgsql
AS $$
DECLARE
    waybill_id INTEGER;
    product_info JSONB;
BEGIN
    -- Вставить новую накладную
    INSERT INTO waybills(customer_id, date, destination)
    VALUES (customer_id, date_param, destination_id)
    RETURNING id INTO waybill_id;

    -- Вставить продукты в накладную
    FOR product_info IN SELECT * FROM jsonb_array_elements(products_data::jsonb)
    LOOP
        INSERT INTO waybil_products(product_id, waybil_id, quantity, price)
        VALUES ((product_info->>'id')::INTEGER, waybill_id, (product_info->>'count')::INTEGER, (product_info->>'price')::DECIMAL(18,2));
    END LOOP;

    result_waybill_id := waybill_id;

EXCEPTION
    -- В случае ошибки откатить транзакцию
    WHEN OTHERS THEN
        ROLLBACK;
        RAISE;
END;
$$;




CREATE OR REPLACE PROCEDURE update_waybill(
    IN waybill_id INTEGER,
    IN waybill_customer_id UUID,
    IN date_param DATE,
    IN destination_id INTEGER,
    IN products_data VARCHAR
)
LANGUAGE plpgsql
AS $$
DECLARE
    product_info JSONB;
BEGIN
    -- Обновить информацию о накладной
    UPDATE waybills
    SET customer_id = waybill_customer_id, date = date_param, destination = destination_id
    WHERE id = waybill_id;

    -- Удалить старые продукты из накладной
    DELETE FROM waybil_products WHERE waybil_id = waybill_id;

    -- Вставить новые продукты в накладную
    FOR product_info IN SELECT * FROM jsonb_array_elements(products_data::jsonb)
    LOOP
        INSERT INTO waybil_products(product_id, waybil_id, quantity, price)
        VALUES ((product_info->>'id')::INTEGER, waybill_id, (product_info->>'count')::INTEGER, (product_info->>'price')::DECIMAL(18,2));
    END LOOP;
END;
$$;


CREATE OR REPLACE PROCEDURE delete_waybill(
    IN waybill_id INTEGER
)
LANGUAGE plpgsql
AS $$
BEGIN
    -- Удалить продукт по имени
    DELETE FROM waybills
    WHERE id = waybill_id;

EXCEPTION
    -- В случае ошибки, PostgreSQL автоматически откатит транзакцию
    WHEN OTHERS THEN
        RAISE;
END;
$$;



CREATE VIEW max_waybills_per_date AS
WITH WaybilPrices AS (
    SELECT
        wp.waybil_id AS id,
        wb.date AS waybill_date,
        c.name AS customer_name, -- изменено на name из таблицы customers
        SUM(wp.price) AS total_price
    FROM
        waybil_products wp
    JOIN
        waybills wb ON wp.waybil_id = wb.id
    JOIN
        customers c ON wb.customer_id = c.id -- добавлено соединение с таблицей customers
    GROUP BY
        wp.waybil_id, wb.date, c.name
),
MaxTotalPricePerDate AS (
    SELECT
        wp.waybill_date,
        MAX(wp.total_price) AS max_total_price
    FROM
        WaybilPrices wp
    GROUP BY
        wp.waybill_date
)SELECT
    wp.id,
    wp.customer_name, -- изменено на customer_name
    wp.waybill_date,
    wp.total_price
FROM
    WaybilPrices wp
JOIN
    MaxTotalPricePerDate mtp ON wp.waybill_date = mtp.waybill_date AND wp.total_price = mtp.max_total_price;





CREATE OR REPLACE FUNCTION find_price_by_product_id(
    product_id integer, 
    start_date date, 
    end_date date
) 
RETURNS TABLE (
    "name" varchar,
    "date" date,
    "price" numeric
) AS $$
BEGIN
    RETURN QUERY
    SELECT 
        p.name AS "name", 
        w.date::date AS "date", 
        SUM(wp.price) / SUM(wp.quantity) AS "price"
    FROM 
        waybil_products wp
    JOIN 
        waybills w ON wp.waybil_id = w.id
    JOIN 
        products p ON wp.product_id = p.id
    WHERE 
        p.id = find_price_by_product_id.product_id
        AND w.date BETWEEN find_price_by_product_id.start_date AND find_price_by_product_id.end_date
    GROUP BY 
        p.name, w.date::date -- Приводим тип к date
    ORDER BY 
        w.date::date; -- Приводим тип к date
END;
$$ LANGUAGE plpgsql;