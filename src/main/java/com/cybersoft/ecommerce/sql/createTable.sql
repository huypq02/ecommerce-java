create database if not exists ecommerce;

USE ecommerce

 -- Me
create table if not exists product(
	id INT NOT NULL AUTO_INCREMENT,
	name varchar(100),
	note text,
	rate double,
	PRIMARY KEY (id)
)

create table if not exists product_detail(
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

CREATE table if not exists image(
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
create table if not exists review(
	id int auto_increment primary key,
	user_id int,
	product_id int,
	date date,
	 rate DECIMAL(3,2) CHECK (rate BETWEEN 1.00 AND 5.00),
	review text
)

create table if not exists users (
	id INT AUTO_INCREMENT PRIMARY KEY,
	user_info_id INT,
	role_id INT,
	email VARCHAR(320),
	password VARCHAR(255),
	oauth_id VARCHAR(255) -- for login with social network
);

ALTER TABLE review ADD CONSTRAINT FK_user_id_review 
FOREIGN KEY(user_id) REFERENCES users(id)
ON DELETE CASCADE
ON UPDATE CASCADE;

ALTER TABLE review ADD CONSTRAINT FK_product_id_review 
FOREIGN KEY(product_id) REFERENCES product(id)
ON DELETE CASCADE
ON UPDATE CASCADE;

create table if not exists product_category(
	id int auto_increment primary key,
	category_id int,
	product_id int
)

create table if not exists category(
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
create table if not exists user_info (
	id INT AUTO_INCREMENT PRIMARY KEY,
	full_name VARCHAR (100),
	gender VARCHAR (6),
	birthday TIMESTAMP,
	address VARCHAR (255),
	phone VARCHAR (10),
	description TEXT	
);


create table if not exists roles (
	id INT AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR (20),
	role VARCHAR (10)
);

ALTER TABLE users ADD CONSTRAINT FK_user_info_id_user
FOREIGN KEY (user_info_id) REFERENCES user_info (id)
ON DELETE CASCADE 
ON UPDATE CASCADE; -- Delete/Update child if Delete/Update father

ALTER TABLE users ADD CONSTRAINT FK_role_id_user
FOREIGN KEY (role_id) REFERENCES roles (id)
ON DELETE CASCADE
ON UPDATE CASCADE;

-- Mr. Thuong
create table if not exists cart (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_info_id INT NOT NULL
);

ALTER TABLE cart
ADD CONSTRAINT FK_user_info_cart
FOREIGN KEY (user_info_id)
REFERENCES user_info(id)
ON DELETE CASCADE
ON UPDATE CASCADE;

create table if not exists cart_detail (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cart_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL CHECK (quantity > 0)
);

ALTER TABLE cart_detail
ADD CONSTRAINT FK_cart_id
FOREIGN KEY (cart_id)
REFERENCES cart(id)
ON DELETE CASCADE
ON UPDATE CASCADE;

ALTER TABLE cart_detail
ADD CONSTRAINT FK_product_id
FOREIGN KEY (product_id)
REFERENCES product(id)
ON DELETE CASCADE
ON UPDATE CASCADE;

create table if not exists orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_info_id INT NOT NULL,
    date DATE NOT NULL,
    payment_method ENUM('Cash', 'Card', 'Online') NOT NULL,
    status ENUM('Pending', 'Completed', 'Cancelled') NOT NULL
);

ALTER TABLE orders
ADD CONSTRAINT FK_user_info_order
FOREIGN KEY (user_info_id)
REFERENCES user_info(id)
ON DELETE CASCADE
ON UPDATE CASCADE;
