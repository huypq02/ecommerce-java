CREATE DATABASE IF EXISTS ecommerce;

USE ecommerce

-- Huy Pham
CREATE TABLE IF EXISTS product(
        id INT NOT NULL AUTO_INCREMENT,
        name varchar(100),
        note text,
        rate double,
        PRIMARY KEY (id)
)

CREATE TABLE IF EXISTS  product_detail(
        id INT NOT NULL AUTO_INCREMENT,
        product_id INT,
        color varchar(40),
        size varchar(10),
        quantity_stock int,
        price double,
        PRIMARY KEY (id)
)

ALTER TABLE product_detail ADD CONSTRAINT FK_product_id_product_detail
FOREIGN KEY (product_id) REFERENCES product(id)
ON DELETE CASCADE
ON UPDATE CASCADE;

CREATE TABLE IF EXISTS image(
        id INT NOT NULL AUTO_INCREMENT,
        product_detail_id INT,
        url text,
        all_text text,
        PRIMARY KEY (id)
)

ALTER TABLE image ADD CONSTRAINT FK_product_detail_id_image
foreign key (product_detail_id) REFERENCES product_detail(id)
ON DELETE CASCADE
ON UPDATE CASCADE;

-- Mr. Tin
CREATE TABLE IF EXISTS review(
        id int auto_increment primary key,
        user_id int,
        product_id int,
        date date,
        rate DECIMAL(3,2) CHECK (rate BETWEEN 1.00 AND 5.00),
        review text
)

CREATE TABLE IF EXISTS user (
        id INT AUTO_INCREMENT PRIMARY KEY,
        user_info_id INT,
        role_id INT,
        email VARCHAR(320),
        password VARCHAR(255)
);

ALTER TABLE review ADD CONSTRAINT FK_user_id_review
FOREIGN KEY(user_id) REFERENCES user(id)
ON DELETE CASCADE
ON UPDATE CASCADE;

ALTER TABLE review ADD CONSTRAINT FK_product_id_review
FOREIGN KEY(product_id) REFERENCES product(id)
ON DELETE CASCADE
ON UPDATE CASCADE;

CREATE TABLE IF EXISTS product_category(
        id int auto_increment primary key,
        category_id int,
        product_id int
)

CREATE TABLE IF EXISTS category(
        id int auto_increment primary key,
        category nvarchar(50)
)

ALTER TABLE product_category ADD CONSTRAINT FK_category_id_product_category
FOREIGN KEY(category_id) REFERENCES category(id)
ON DELETE CASCADE
ON UPDATE CASCADE;

ALTER TABLE product_category ADD CONSTRAINT FK_product_id_product_category
FOREIGN KEY(product_id) REFERENCES product(id)
ON DELETE CASCADE
ON UPDATE CASCADE;

-- Nga
CREATE TABLE  IF EXISTS user_info (
        id INT AUTO_INCREMENT PRIMARY KEY,
        full_name VARCHAR (100),
        gender VARCHAR (6),
        birthday TIMESTAMP,
        address VARCHAR (255),
        phone VARCHAR (10),
        description TEXT
);


CREATE TABLE  IF EXISTS role (
        id INT AUTO_INCREMENT PRIMARY KEY,
        name VARCHAR (20),
        role VARCHAR (10)
);

ALTER TABLE user ADD CONSTRAINT FK_user_info_id_user
FOREIGN KEY (user_info_id) REFERENCES user_info (id)
ON DELETE CASCADE
ON UPDATE CASCADE; -- Delete/Update child if Delete/Update father

ALTER TABLE user ADD CONSTRAINT FK_role_id_user
FOREIGN KEY (role_id) REFERENCES role (id)
ON DELETE CASCADE
ON UPDATE CASCADE;

-- Mr. Thuong
CREATE TABLE  IF EXISTS cart (
        id INT AUTO_INCREMENT PRIMARY KEY,
        user_info_id INT NOT NULL
);

ALTER TABLE  IF EXISTS cart
ADD CONSTRAINT FK_user_info_cart
FOREIGN KEY (user_info_id)
REFERENCES user_info(id)
ON DELETE CASCADE
ON UPDATE CASCADE;

CREATE TABLE  IF EXISTS cart_detail (
        id INT AUTO_INCREMENT PRIMARY KEY,
        cart_id INT NOT NULL,
        product_id INT NOT NULL,
        quantity INT NOT NULL CHECK (quantity > 0)
);

ALTER TABLE  IF EXISTS cart_detail
ADD CONSTRAINT FK_cart_id
FOREIGN KEY (cart_id)
REFERENCES cart(id)
ON DELETE CASCADE
ON UPDATE CASCADE;

ALTER TABLE  IF EXISTS cart_detail
ADD CONSTRAINT FK_product_id
FOREIGN KEY (product_id)
REFERENCES product(id)
ON DELETE CASCADE
ON UPDATE CASCADE;

CREATE TABLE  IF EXISTS orders (
        id INT AUTO_INCREMENT PRIMARY KEY,
        user_info_id INT NOT NULL,
        date DATE NOT NULL,
        payment_method ENUM('Cash', 'Card', 'Online') NOT NULL,
        status ENUM('Pending', 'Completed', 'Cancelled') NOT NULL
);

ALTER TABLE IF EXISTS orders
ADD CONSTRAINT FK_user_info_order
FOREIGN KEY (user_info_id)
REFERENCES user_info(id)
ON DELETE CASCADE
ON UPDATE CASCADE;