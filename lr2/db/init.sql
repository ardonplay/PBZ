SET datestyle TO 'ISO, DMY';
CREATE TYPE "product_type" AS ENUM (
    'industrial',
    'household',
    'commerce'
);

CREATE TYPE "person_type" AS ENUM ( 'individual', 'legal' );

CREATE TABLE
    "products" (
        "id" SERIAL PRIMARY KEY,
        "price_id" integer UNIQUE,
        "name" varchar NOT NULL,
        "type" product_type NOT NULL
    );
    

CREATE TABLE "customers" (
    "id" uuid NOT NULL DEFAULT (gen_random_uuid()) PRIMARY KEY,
    "name" varchar NOT NULL,
    "type" person_type NOT NULL,
    "adress" varchar NOT NULL,
    "phone_number" VARCHAR(11) UNIQUE NOT NULL,
    "bank_details_id" SERIAL UNIQUE
);

CREATE TABLE "bank_details" (
    "id" INT PRIMARY KEY,
    "number" VARCHAR NOT NULL,
    "bank_name" varchar NOT NULL,
    FOREIGN KEY ("id") REFERENCES "customers" ("bank_details_id") ON DELETE CASCADE
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
        "destination" integer NOT NULL,
        FOREIGN KEY ("customer_id") REFERENCES "customers" ("id") ON DELETE CASCADE,
        FOREIGN KEY ("destination") REFERENCES "destinations" ("id") ON DELETE CASCADE
    );

CREATE TABLE
    "price_and_quantity" (
        "id" SERIAL PRIMARY KEY,
        "date" date NOT NULL,
        "price" BIGINT NOT NULL,
        "quantity" integer NOT NULL,
        FOREIGN KEY ("id") REFERENCES "products" ("price_id") ON DELETE CASCADE
    );

CREATE TABLE
    "waybil_products" (
        "id" SERIAL PRIMARY KEY,
        "product_id" integer NOT NULL,
        "waybil_id" integer NOT NULL,
        FOREIGN KEY ("product_id") REFERENCES "products" ("id") ON DELETE CASCADE,
        FOREIGN KEY ("waybil_id") REFERENCES "waybills" ("id") ON DELETE CASCADE
    );


INSERT INTO "products" ("price_id", "name", "type") VALUES
(1, 'Холодильник ATLANT ХМ 4626-109 ND', 'household'),
(2, 'Ленточнопильный станок Stalex BS-85', 'industrial'),
(3, 'Компактный банкомат NCR SelfServ 22', 'commerce'),
(4, 'Блок питания Deepcool PF600 (R-PF600D-HA0B-EU) 600W, APFC, 80 Plus', 'household'),
(5, 'Посудомоечная машина Electrolux EEA12100L', 'household');

INSERT INTO "price_and_quantity" ("date", "price", "quantity") VALUES
('27/10/2023', 1553, 10),
('26/10/2023', 9593.78,3),
('25/10/2023', 7564.22, 7),
('24/09/2023', 154.90, 156),
('30/08/2023', 1510.30, 6);


INSERT INTO "customers" ("name", "type", "adress", "phone_number") VALUES
('Иванов Иван', 'individual', 'ул. Ленина, 123', '1234567890'),
('Владимир Мощук', 'individual', 'ул. Кунцевщина, 38', '298277252'),
('ЧП Сепия', 'legal', 'ул. Притыцкого, 105 — 194', '296291276');

INSERT INTO "bank_details" ("id", "number", "bank_name")
VALUES  (1,'1234567890', 'Bank of Example'),
        (2,'9876543210', 'Another Bank'),
        (3,'5555555555', 'Bank XYZ');


INSERT INTO "destinations" ("name", "region", "country") VALUES
('Минск', 'Минск', 'Беларусь');



INSERT INTO "waybills" ("customer_id", "destination") VALUES
((SELECT customers.id FROM "customers" LIMIT 1 OFFSET 0),1),
((SELECT customers.id FROM "customers" LIMIT 1 OFFSET 1),1),
((SELECT customers.id FROM "customers" LIMIT 1 OFFSET 2),1);

INSERT INTO "waybil_products" ("product_id", "waybil_id") VALUES
(1, 1),
(2, 2),
(3,3);
