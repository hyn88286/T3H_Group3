CREATE DATABASE pet_shop_1;
USE pet_shop_1;

CREATE TABLE user
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    fullname      NVARCHAR(255) NULL,
    username      VARCHAR(50),
    password      VARCHAR(255),
    email         VARCHAR(100),
    phone         VARCHAR(20),
    address       NVARCHAR(500),
    deleted       BOOLEAN  DEFAULT false,
    created_by    VARCHAR(50),
    created_date  DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_by   VARCHAR(50),
    modified_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE role
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    name          VARCHAR(255) NULL,
    code          VARCHAR(50),
    deleted       BOOLEAN  DEFAULT false,
    created_by    VARCHAR(50),
    created_date  DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_by   VARCHAR(50),
    modified_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE user_role
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id       BIGINT NOT NULL,
    role_id       BIGINT NOT NULL,
    deleted       BOOLEAN  DEFAULT false,
    created_by    VARCHAR(50),
    created_date  DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_by   VARCHAR(50),
    modified_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user (id),
    FOREIGN KEY (role_id) REFERENCES role (id)
);

CREATE TABLE category
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    name          NVARCHAR(100),
    code          VARCHAR(50),
    deleted       BOOLEAN  DEFAULT false,
    created_by    VARCHAR(50),
    created_date  DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_by   VARCHAR(50),
    modified_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE product
(
    id                BIGINT AUTO_INCREMENT PRIMARY KEY,
    name              NVARCHAR(100),
    description       NVARCHAR(1000),
    image             VARCHAR(100),
    code              VARCHAR(50),
    quantity          INT,
    price             FLOAT,
    short_description NVARCHAR(1000),
    category_id       BIGINT NOT NULL,
    deleted           BOOLEAN  DEFAULT false,
    created_by        VARCHAR(50),
    created_date      DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_by       VARCHAR(50),
    modified_date     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES category (id)
);

CREATE TABLE size
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    name          VARCHAR(255) NULL,
    weight        INT,
    code          VARCHAR(50)  NULL,
    deleted       BOOLEAN  DEFAULT false,
    created_by    VARCHAR(50),
    created_date  DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_by   VARCHAR(50),
    modified_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE cart
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id       BIGINT NOT NULL,
    product_id    BIGINT NOT NULL,
    size_id       BIGINT NOT NULL,
    quantity      INT,
    total         FLOAT,
    deleted       BOOLEAN  DEFAULT false,
    created_by    VARCHAR(50),
    created_date  DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_by   VARCHAR(50),
    modified_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user (id),
    FOREIGN KEY (product_id) REFERENCES product (id),
    FOREIGN KEY (size_id) REFERENCES size (id)
);

CREATE TABLE order_master
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    code             VARCHAR(100),
    user_id          BIGINT NOT NULL,
    total_amount     FLOAT,
    address_shipping NVARCHAR(500),
    phone_shipping   VARCHAR(20),
    deleted          BOOLEAN  DEFAULT false,
    status           INT,
    created_by       VARCHAR(50),
    created_date     DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_by      VARCHAR(50),
    modified_date    DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user (id)
);

CREATE TABLE order_detail
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id      BIGINT NOT NULL,
    product_id    BIGINT NOT NULL,
    size_id       BIGINT NOT NULL,
    quantity      INT,
    total         FLOAT,
    deleted       BOOLEAN  DEFAULT false,
    created_by    VARCHAR(50),
    created_date  DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_by   VARCHAR(50),
    modified_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES order_master (id),
    FOREIGN KEY (product_id) REFERENCES product (id),
    FOREIGN KEY (size_id) REFERENCES size (id)
);

CREATE TABLE comment
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id       BIGINT NOT NULL,
    product_id    BIGINT NOT NULL,
    content       NVARCHAR(1000),
    deleted       BOOLEAN  DEFAULT false,
    created_by    VARCHAR(50),
    created_date  DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_by   VARCHAR(50),
    modified_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user (id),
    FOREIGN KEY (product_id) REFERENCES product (id)
);

CREATE TABLE rating
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id       BIGINT NOT NULL,
    product_id    BIGINT NOT NULL,
    star          INT,
    deleted       BOOLEAN  DEFAULT false,
    created_by    VARCHAR(50),
    created_date  DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_by   VARCHAR(50),
    modified_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user (id),
    FOREIGN KEY (product_id) REFERENCES product (id)
);

create table product_size
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    size_id       BIGINT NOT NULL,
    product_id    BIGINT NOT NULL,
    deleted       BOOLEAN  DEFAULT false,
    created_by    VARCHAR(50),
    created_date  DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_by   VARCHAR(50),
    modified_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES product (id),
    FOREIGN KEY (size_id) REFERENCES size (id)
);

-- Master data
INSERT INTO pet_shop_1.size (name, weight, code)
VALUES ('Size S', 1, 'SIZE_S');
INSERT INTO pet_shop_1.size (name, weight, code)
VALUES ('Size M', 2, 'SIZE_M');
INSERT INTO pet_shop_1.size (name, weight, code)
VALUES ('Size L', 5, 'SIZE_L');
INSERT INTO pet_shop_1.size (name, weight, code)
VALUES ('Size XL', 10, 'SIZE_XL');
-- role
INSERT INTO pet_shop_1.role (name)
VALUES ('ROLE_ADMIN'),
       ('ROLE_USER');
INSERT INTO pet_shop_1.user (username, password)
VALUES ('admin', '$2a$12$ks4EOu8Szdxm5pJMBF5fU.4Nj6HAK8RPoEJ4Izm7VZ6Pz0kIJ34.S'),
       ('user', '$2a$12$d2I8cj9kt1jTZtOpyU.mKunnv3WDxVL2tuAGHVskncRIdq9XYtnYG');
INSERT INTO pet_shop_1.user_role (user_id, role_id)
VALUES (1, 1),
       (2, 2);

INSERT INTO pet_shop_1.category (name, code)
VALUES ('Category Test 1', 'C001'),
       ('Category Test 2', 'C002');

INSERT INTO pet_shop_1.product (name, description, code, quantity, price, short_description, category_id)
VALUES ('Product Test 1',
        'Ngô, cám lúa mì, bột gia cầm, sắn, bột hạt cọ, mỡ gia cầm, khoáng chất (canxi carbonate, monocalcium phosphate, natri clorua, kali clorua, sắt ii sulfate, đồng sulfate, ôxít mangan, cobalt sulfate, ôxít kẽm, kali iodua, selen), vitamin (vitamin a, d, e, k, thiamine (b1), riboflavin (b2), niacin (b3), axít panthothenic (b5), pyridoxine (b6), cobalamin (b12), biotin, axít folic, colin), chất chống oxy hóa, màu thực phẩm.',
        'A001', 10, 100000, 'HẠT ZOI DOG CHO CÚN TRƯỞNG THÀNH TRÊN 12 THÁNG TUỔI', 1),
       ('Product Test 2',
        'Ngô, cám lúa mì, bột gia cầm, sắn, bột hạt cọ, mỡ gia cầm, khoáng chất (canxi carbonate, monocalcium phosphate, natri clorua, kali clorua, sắt ii sulfate, đồng sulfate, ôxít mangan, cobalt sulfate, ôxít kẽm, kali iodua, selen), vitamin (vitamin a, d, e, k, thiamine (b1), riboflavin (b2), niacin (b3), axít panthothenic (b5), pyridoxine (b6), cobalamin (b12), biotin, axít folic, colin), chất chống oxy hóa, màu thực phẩm.',
        'A002', 10, 200000, 'HẠT ZOI DOG CHO CÚN TRƯỞNG THÀNH TRÊN 12 THÁNG TUỔI', 2),
       ('Product Test 3',
        'Ngô, cám lúa mì, bột gia cầm, sắn, bột hạt cọ, mỡ gia cầm, khoáng chất (canxi carbonate, monocalcium phosphate, natri clorua, kali clorua, sắt ii sulfate, đồng sulfate, ôxít mangan, cobalt sulfate, ôxít kẽm, kali iodua, selen), vitamin (vitamin a, d, e, k, thiamine (b1), riboflavin (b2), niacin (b3), axít panthothenic (b5), pyridoxine (b6), cobalamin (b12), biotin, axít folic, colin), chất chống oxy hóa, màu thực phẩm.',
        'A003', 10, 300000, 'HẠT ZOI DOG CHO CÚN TRƯỞNG THÀNH TRÊN 12 THÁNG TUỔI', 1),
       ('Product Test 4',
        'Ngô, cám lúa mì, bột gia cầm, sắn, bột hạt cọ, mỡ gia cầm, khoáng chất (canxi carbonate, monocalcium phosphate, natri clorua, kali clorua, sắt ii sulfate, đồng sulfate, ôxít mangan, cobalt sulfate, ôxít kẽm, kali iodua, selen), vitamin (vitamin a, d, e, k, thiamine (b1), riboflavin (b2), niacin (b3), axít panthothenic (b5), pyridoxine (b6), cobalamin (b12), biotin, axít folic, colin), chất chống oxy hóa, màu thực phẩm.',
        'A004', 10, 100000, 'HẠT ZOI DOG CHO CÚN TRƯỞNG THÀNH TRÊN 12 THÁNG TUỔI', 2),
       ('Product Test 5',
        'Ngô, cám lúa mì, bột gia cầm, sắn, bột hạt cọ, mỡ gia cầm, khoáng chất (canxi carbonate, monocalcium phosphate, natri clorua, kali clorua, sắt ii sulfate, đồng sulfate, ôxít mangan, cobalt sulfate, ôxít kẽm, kali iodua, selen), vitamin (vitamin a, d, e, k, thiamine (b1), riboflavin (b2), niacin (b3), axít panthothenic (b5), pyridoxine (b6), cobalamin (b12), biotin, axít folic, colin), chất chống oxy hóa, màu thực phẩm.',
        'A005', 10, 400000, 'HẠT ZOI DOG CHO CÚN TRƯỞNG THÀNH TRÊN 12 THÁNG TUỔI', 1),
       ('Product Test 6',
        'Ngô, cám lúa mì, bột gia cầm, sắn, bột hạt cọ, mỡ gia cầm, khoáng chất (canxi carbonate, monocalcium phosphate, natri clorua, kali clorua, sắt ii sulfate, đồng sulfate, ôxít mangan, cobalt sulfate, ôxít kẽm, kali iodua, selen), vitamin (vitamin a, d, e, k, thiamine (b1), riboflavin (b2), niacin (b3), axít panthothenic (b5), pyridoxine (b6), cobalamin (b12), biotin, axít folic, colin), chất chống oxy hóa, màu thực phẩm.',
        'A006', 10, 500000, 'HẠT ZOI DOG CHO CÚN TRƯỞNG THÀNH TRÊN 12 THÁNG TUỔI', 2),
       ('Product Test 7',
        'Ngô, cám lúa mì, bột gia cầm, sắn, bột hạt cọ, mỡ gia cầm, khoáng chất (canxi carbonate, monocalcium phosphate, natri clorua, kali clorua, sắt ii sulfate, đồng sulfate, ôxít mangan, cobalt sulfate, ôxít kẽm, kali iodua, selen), vitamin (vitamin a, d, e, k, thiamine (b1), riboflavin (b2), niacin (b3), axít panthothenic (b5), pyridoxine (b6), cobalamin (b12), biotin, axít folic, colin), chất chống oxy hóa, màu thực phẩm.',
        'A007', 10, 200000, 'HẠT ZOI DOG CHO CÚN TRƯỞNG THÀNH TRÊN 12 THÁNG TUỔI', 1),
       ('Product Test 8',
        'Ngô, cám lúa mì, bột gia cầm, sắn, bột hạt cọ, mỡ gia cầm, khoáng chất (canxi carbonate, monocalcium phosphate, natri clorua, kali clorua, sắt ii sulfate, đồng sulfate, ôxít mangan, cobalt sulfate, ôxít kẽm, kali iodua, selen), vitamin (vitamin a, d, e, k, thiamine (b1), riboflavin (b2), niacin (b3), axít panthothenic (b5), pyridoxine (b6), cobalamin (b12), biotin, axít folic, colin), chất chống oxy hóa, màu thực phẩm.',
        'A008', 10, 300000, 'HẠT ZOI DOG CHO CÚN TRƯỞNG THÀNH TRÊN 12 THÁNG TUỔI', 2),
       ('Product Test 9',
        'Ngô, cám lúa mì, bột gia cầm, sắn, bột hạt cọ, mỡ gia cầm, khoáng chất (canxi carbonate, monocalcium phosphate, natri clorua, kali clorua, sắt ii sulfate, đồng sulfate, ôxít mangan, cobalt sulfate, ôxít kẽm, kali iodua, selen), vitamin (vitamin a, d, e, k, thiamine (b1), riboflavin (b2), niacin (b3), axít panthothenic (b5), pyridoxine (b6), cobalamin (b12), biotin, axít folic, colin), chất chống oxy hóa, màu thực phẩm.',
        'A009', 10, 400000, 'HẠT ZOI DOG CHO CÚN TRƯỞNG THÀNH TRÊN 12 THÁNG TUỔI', 1),
       ('Product Test 10',
        'Ngô, cám lúa mì, bột gia cầm, sắn, bột hạt cọ, mỡ gia cầm, khoáng chất (canxi carbonate, monocalcium phosphate, natri clorua, kali clorua, sắt ii sulfate, đồng sulfate, ôxít mangan, cobalt sulfate, ôxít kẽm, kali iodua, selen), vitamin (vitamin a, d, e, k, thiamine (b1), riboflavin (b2), niacin (b3), axít panthothenic (b5), pyridoxine (b6), cobalamin (b12), biotin, axít folic, colin), chất chống oxy hóa, màu thực phẩm.',
        'A0010', 10, 500000, 'HẠT ZOI DOG CHO CÚN TRƯỞNG THÀNH TRÊN 12 THÁNG TUỔI', 2),
       ('Product Test 11',
        'Ngô, cám lúa mì, bột gia cầm, sắn, bột hạt cọ, mỡ gia cầm, khoáng chất (canxi carbonate, monocalcium phosphate, natri clorua, kali clorua, sắt ii sulfate, đồng sulfate, ôxít mangan, cobalt sulfate, ôxít kẽm, kali iodua, selen), vitamin (vitamin a, d, e, k, thiamine (b1), riboflavin (b2), niacin (b3), axít panthothenic (b5), pyridoxine (b6), cobalamin (b12), biotin, axít folic, colin), chất chống oxy hóa, màu thực phẩm.',
        'A0011', 10, 100000, 'HẠT ZOI DOG CHO CÚN TRƯỞNG THÀNH TRÊN 12 THÁNG TUỔI', 1),
       ('Product Test 12',
        'Ngô, cám lúa mì, bột gia cầm, sắn, bột hạt cọ, mỡ gia cầm, khoáng chất (canxi carbonate, monocalcium phosphate, natri clorua, kali clorua, sắt ii sulfate, đồng sulfate, ôxít mangan, cobalt sulfate, ôxít kẽm, kali iodua, selen), vitamin (vitamin a, d, e, k, thiamine (b1), riboflavin (b2), niacin (b3), axít panthothenic (b5), pyridoxine (b6), cobalamin (b12), biotin, axít folic, colin), chất chống oxy hóa, màu thực phẩm.',
        'A0012', 10, 200000, 'HẠT ZOI DOG CHO CÚN TRƯỞNG THÀNH TRÊN 12 THÁNG TUỔI', 2),
       ('Product Test 13',
        'Ngô, cám lúa mì, bột gia cầm, sắn, bột hạt cọ, mỡ gia cầm, khoáng chất (canxi carbonate, monocalcium phosphate, natri clorua, kali clorua, sắt ii sulfate, đồng sulfate, ôxít mangan, cobalt sulfate, ôxít kẽm, kali iodua, selen), vitamin (vitamin a, d, e, k, thiamine (b1), riboflavin (b2), niacin (b3), axít panthothenic (b5), pyridoxine (b6), cobalamin (b12), biotin, axít folic, colin), chất chống oxy hóa, màu thực phẩm.',
        'A0013', 10, 300000, 'HẠT ZOI DOG CHO CÚN TRƯỞNG THÀNH TRÊN 12 THÁNG TUỔI', 1),
       ('Product Test 14',
        'Ngô, cám lúa mì, bột gia cầm, sắn, bột hạt cọ, mỡ gia cầm, khoáng chất (canxi carbonate, monocalcium phosphate, natri clorua, kali clorua, sắt ii sulfate, đồng sulfate, ôxít mangan, cobalt sulfate, ôxít kẽm, kali iodua, selen), vitamin (vitamin a, d, e, k, thiamine (b1), riboflavin (b2), niacin (b3), axít panthothenic (b5), pyridoxine (b6), cobalamin (b12), biotin, axít folic, colin), chất chống oxy hóa, màu thực phẩm.',
        'A0014', 10, 400000, 'HẠT ZOI DOG CHO CÚN TRƯỞNG THÀNH TRÊN 12 THÁNG TUỔI', 2),
       ('Product Test 15',
        'Ngô, cám lúa mì, bột gia cầm, sắn, bột hạt cọ, mỡ gia cầm, khoáng chất (canxi carbonate, monocalcium phosphate, natri clorua, kali clorua, sắt ii sulfate, đồng sulfate, ôxít mangan, cobalt sulfate, ôxít kẽm, kali iodua, selen), vitamin (vitamin a, d, e, k, thiamine (b1), riboflavin (b2), niacin (b3), axít panthothenic (b5), pyridoxine (b6), cobalamin (b12), biotin, axít folic, colin), chất chống oxy hóa, màu thực phẩm.',
        'A0014', 10, 100000, 'HẠT ZOI DOG CHO CÚN TRƯỞNG THÀNH TRÊN 12 THÁNG TUỔI', 1),
       ('Product Test 16',
        'Ngô, cám lúa mì, bột gia cầm, sắn, bột hạt cọ, mỡ gia cầm, khoáng chất (canxi carbonate, monocalcium phosphate, natri clorua, kali clorua, sắt ii sulfate, đồng sulfate, ôxít mangan, cobalt sulfate, ôxít kẽm, kali iodua, selen), vitamin (vitamin a, d, e, k, thiamine (b1), riboflavin (b2), niacin (b3), axít panthothenic (b5), pyridoxine (b6), cobalamin (b12), biotin, axít folic, colin), chất chống oxy hóa, màu thực phẩm.',
        'A0015', 10, 500000, 'HẠT ZOI DOG CHO CÚN TRƯỞNG THÀNH TRÊN 12 THÁNG TUỔI', 2);

INSERT INTO pet_shop_1.product_size (size_id, product_id)
VALUES (2, 1),
       (1, 1),
       (3, 1),
       (4, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (1, 5),
       (1, 6),
       (1, 7),
       (1, 8),
       (1, 9),
       (1, 10),
       (1, 11),
       (1, 12),
       (1, 13),
       (1, 14),
       (1, 15),
       (1, 16);