SET datestyle TO 'ISO, DMY';
CREATE TABLE "product_type"(
    "id" SERIAL PRIMARY KEY,
    "type" VARCHAR NOT NULL
);

CREATE TYPE "person_type" AS ENUM ( 'INDIVIDUAL', 'LEGAL' );


CREATE TABLE
    "products" (
        "id" SERIAL PRIMARY KEY,
        "name" varchar NOT NULL,
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
    FOREIGN KEY ("customer_id") REFERENCES "customers" ("id") ON DELETE CASCADE
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
        FOREIGN KEY ("customer_id") REFERENCES "customers" ("id") ON DELETE CASCADE,
        FOREIGN KEY ("destination") REFERENCES "destinations" ("id") ON DELETE CASCADE
    );

CREATE TABLE
    "waybil_products" (
        "id" SERIAL PRIMARY KEY,
        "product_id" integer NOT NULL,
        "waybil_id" integer NOT NULL,
        "quantity" integer NOT NULL,
        "price" BIGINT NOT NULL,
        FOREIGN KEY ("product_id") REFERENCES "products" ("id") ON DELETE CASCADE,
        FOREIGN KEY ("waybil_id") REFERENCES "waybills" ("id") ON DELETE CASCADE
    );


CREATE VIEW waybil_prices AS
SELECT waybil_id AS id, SUM(price) AS total_price
FROM waybil_products
GROUP BY waybil_id;

INSERT INTO "product_type" ("type") VALUES
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
('Минск', 'Минск', 'Беларусь');



INSERT INTO "waybills" ("date", "customer_id", "destination") VALUES
('27/10/2023', (SELECT customers.id FROM "customers" LIMIT 1 OFFSET 0),1),
('25/10/2023', (SELECT customers.id FROM "customers" LIMIT 1 OFFSET 1),1),
('30/08/2023',(SELECT customers.id FROM "customers" LIMIT 1 OFFSET 2),1);


INSERT INTO "waybil_products" ("product_id", "waybil_id", "quantity",
  "price") VALUES
(1, 1, 10, 1553),
(2, 2, 2, 703),
(3,3, 5, 586);