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

CREATE TABLE
    "customers" (
        "id" SERIAL PRIMARY KEY,
        "name" varchar NOT NULL,
        "type" person_type NOT NULL,
        "adress" varchar NOT NULL,
        "phone_number" VARCHAR(11) UNIQUE NOT NULL,
        "document_id" uuid UNIQUE NOT NULL DEFAULT (gen_random_uuid()),
        "bank_details_id" integer UNIQUE NOT NULL
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
        "customer_id" integer NOT NULL,
        "destination" integer NOT NULL,
        FOREIGN KEY ("customer_id") REFERENCES "customers" ("id") ON DELETE CASCADE,
        FOREIGN KEY ("destination") REFERENCES "destinations" ("id") ON DELETE CASCADE
    );

CREATE TABLE
    "price_and_quantity" (
        "id" INTEGER PRIMARY KEY NOT NULL UNIQUE,
        "date" date NOT NULL,
        "price" BIGINT NOT NULL,
        "quantity" integer NOT NULL,
        FOREIGN KEY ("id") REFERENCES "products" ("price_id") ON DELETE CASCADE
    );

CREATE TABLE
    "bank_details" (
        "id" integer PRIMARY KEY NOT NULL,
        "number" VARCHAR NOT NULL,
        "bank_name" varchar NOT NULL,
        FOREIGN KEY ("id") REFERENCES "customers" ("bank_details_id") ON DELETE CASCADE
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

INSERT INTO "price_and_quantity" ("id", "date", "price", "quantity") VALUES
(1, '27/10/2023', 1553, 10),
(2, '26/10/2023', 9593.78,3),
(3, '25/10/2023', 7564.22, 7),
(4, '24/09/2023', 154.90, 156),
(5, '30/08/2023', 1510.30, 6);


INSERT INTO "customers" ("name", "type", "adress", "phone_number", "bank_details_id") VALUES
('Иванов Иван', 'individual', 'ул. Ленина, 123', '1234567890', 1),
('Владимир Мощук', 'individual', 'ул. Кунцевщина, 38', '298277252', 2),
('ЧП Сепия', 'legal', 'ул. Притыцкого, 105 — 194', '296291276', 3);

INSERT INTO "destinations" ("name", "region", "country") VALUES
('Минск', 'Минск', 'Беларусь');


INSERT INTO "waybills" ("customer_id", "destination") VALUES
(1, 1),
(2,1),
(3,1);


INSERT INTO "bank_details" ("id", "number", "bank_name")
VALUES (1, '1234567890', 'Bank of Example'),
(2, '9876543210', 'Another Bank'),
(3, '5555555555', 'Bank XYZ');

INSERT INTO "waybil_products" ("product_id", "waybil_id") VALUES
(1, 1),
(2, 2),
(3,3);