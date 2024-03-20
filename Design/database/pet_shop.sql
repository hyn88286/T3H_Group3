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
    code              VARCHAR(50),
    quantity          INT,
    price             DECIMAL(10, 2),
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
    product_size_id    BIGINT NOT NULL,
    deleted       BOOLEAN  DEFAULT false,
    quantity      INT,
    created_by    VARCHAR(50),
    created_date  DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_by   VARCHAR(50),
    modified_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user (id),
    FOREIGN KEY (product_size_id) REFERENCES product_size (id)
);

create table product_image
(
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id    BIGINT NOT NULL,
    type          VARCHAR(50),
    name          VARCHAR(100),
    file_path     VARCHAR(255),
    deleted       BOOLEAN  DEFAULT false,
    created_by    VARCHAR(50),
    created_date  DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_by   VARCHAR(50),
    modified_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES product (id)
);

CREATE TABLE order_master
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id          BIGINT NOT NULL,
    total_amount     DECIMAL(10, 2),
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
    product_size_id BIGINT NOT NULL,
    quantity      INT,
    total         DECIMAL(10, 2),
    deleted       BOOLEAN  DEFAULT false,
    created_by    VARCHAR(50),
    created_date  DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_by   VARCHAR(50),
    modified_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES order_master (id),
    FOREIGN KEY (product_size_id) REFERENCES product_size (id)
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

