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
VALUES ('Thức ăn cho chó', 'C001'),
       ('Thức ăn cho mèo', 'C002');

INSERT INTO pet_shop_1.product (name, description, code, quantity, price, short_description, category_id, image)
VALUES ('Hạt Zoi Dog thức ăn cho chó trưởng thành',
        'Thức ăn hạt cho chó trưởng thành hạt Zoi Dog Food có thương hiệu từ Thái Lan nổi tiếng về chất lượng, uy tín chắc chắn sẽ đem đến những bữa ăn ngon miệng, giàu chất dinh dưỡng cho các bé cún.',
        'D001', 99, 39000,
        'Thức ăn hạt cho chó trưởng thành hạt Zoi Dog Food có thương hiệu từ Thái Lan nổi tiếng về chất lượng, uy tín',
        1, 'C:\\T3H_Group3\\Source\\Images\\product\\data\\sp_1_1.jpg'),
       ('Thức ăn cho chó lớn Pedigree vị bò',
        'Sản phẩm Pate Cho Chó Pedigree Vị Bò Kho Và Rau Củ với đóng gói dạng túi nhỏ tiện dụng, có thể mang đi du lịch, dễ dàng bảo quản. Hương vị thơm ngon, hấp dẫn. Đặc biệt, sản phẩm có thể kết hợp thêm với các bữa ăn thông thường của cún để kích thích vị giác và bổ sung dinh dưỡng cho chó.',
        'D003', 99, 69000,
        'Sản phẩm Pate Cho Chó Pedigree Vị Bò Kho Và Rau Củ với đóng gói dạng túi nhỏ tiện dụng, có thể mang đi du lịch, dễ dàng bảo quản',
        1, 'C:\\T3H_Group3\\Source\\Images\\product\\data\\sp_1_3.webp'),
       ('Thức ăn cho chó con Pedigree gà và trứng',
        'Thức Ăn Cho Chó Con Pedigree Vị Gà, Trứng Và Sữa chứa nhiều chất dinh dưỡng như canxi giúp xương chắc khỏe, vitamin E giúp cơ thể chuyển hóa nhanh chất dinh dưỡng đi nuôi cơ thể sẽ là một sản phẩm hữu ích toàn diện, ngoài việc để cho những chú cún của bạn có bữa ăn no, thì sản phẩm còn có tác dụng bồi bổ, cung cấp các chất dinh dưỡng cho quá trình phát triển của cún cho từng giai đoạn.',
        'D002', 99, 89000, 'Thức Ăn Cho Chó Con Pedigree Vị Gà, Trứng Và Sữa chứa nhiều chất dinh dưỡng', 1,
        'C:\\T3H_Group3\\Source\\Images\\product\\data\\sp_1_2.webp'),
       ('Thức ăn hạt Golden Pet cho chó trưởng thành',
        'Thức ăn cho chó lớn Golden Pet All Adult Dogs là sản phẩm thức ăn hạt khô. Cung cấp toàn diện dinh dưỡng và khoáng chất thiết yếu cho cún yêu của bạn.Tăng cường hệ miễn dịch nhớ các vitamin, chất chống oxy hóa và khoáng chất bổ sung.Bổ sung Canxi và phốt pho để cún của bạn luôn có xương, khớp và cơ bắp chắc khỏe.',
        'D005', 99, 99000,
        'Thức ăn cho chó lớn Golden Pet All Adult Dogs là sản phẩm thức ăn hạt khô. Cung cấp toàn diện dinh dưỡng và khoáng chất thiết yếu cho cún yêu của bạn',
        1, 'C:\\T3H_Group3\\Source\\Images\\product\\data\\sp_1_5.jpg');

INSERT INTO pet_shop_1.product_size (size_id, product_id)
VALUES (1, 1),
       (2, 1),
       (3, 1),
       (1, 2),
       (2, 2),
       (3, 2),
       (1, 3),
       (2, 3),
       (1, 4);
