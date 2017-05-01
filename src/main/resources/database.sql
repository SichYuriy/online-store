CREATE TABLE IF NOT EXISTS `category` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
    `title` VARCHAR(75) NOT NULL,
    PRIMARY KEY(`id`)
);

CREATE TABLE IF NOT EXISTS `product` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
    `title` VARCHAR(75) NOT NULL,
    `description` VARCHAR(512) NULL DEFAULT NULL, 
    `category_id` INT(11) NOT NULL,
    `price` DECIMAL(10, 4) NOT NULL,
    `count` INT NOT NULL,
    `enabled` TINYINT(1)  NOT NULL DEFAULT '1',
    `votes_count` INT NOT NULL DEFAULT '0',
    `avg_rating` DECIMAL(3, 2) NULL,
    `main_image_url` VARCHAR(512) NOT NULL,
    PRIMARY KEY(`id`),
    CONSTRAINT `fk_product_category`
		FOREIGN KEY(`category_id`)
        REFERENCES `category` (`id`)
);

ALTER TABLE `product` MODIFY `count` INT UNSIGNED NOT NULL;

CREATE TABLE IF NOT EXISTS `user` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
    `login` VARCHAR(45) NOT NULL UNIQUE,
    `password` VARCHAR(45) NOT NULL,
    `black_list` TINYINT(1) NOT NULL DEFAULT '0',
    PRIMARY KEY(`id`)
);

CREATE TABLE IF NOT EXISTS `role` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) NOT NULL,
    PRIMARY KEY(`id`)
);

CREATE TABLE IF NOT EXISTS `user_role` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`user_id` INT(11) NOT NULL,
	`role_id` INT(11) NOT NULL,
	PRIMARY KEY (`id`),
	INDEX `fk_user_role_user_index` (`user_id` ASC),
	INDEX `fk_user_role_roel_index` (`role_id` ASC),
	CONSTRAINT `fk_user_role_role`
		FOREIGN KEY (`role_id`)
		REFERENCES `role` (`id`),
	CONSTRAINT `fk_user_role_user`
		FOREIGN KEY (`user_id`)
		REFERENCES `user` (`id`)
);

CREATE TABLE IF NOT EXISTS `order` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
    `date` DATETIME NOT NULL,
    `status` VARCHAR(45) NOT NULL,
    `user_id` INT(11) NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_order_user_index` (`user_id` ASC),
    CONSTRAINT `fk_order_user`
		FOREIGN KEY (`user_id`)
        REFERENCES `user` (`id`)
);

CREATE TABLE IF NOT EXISTS `line_item` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
    `order_id` INT(11) NOT NULL,
    `product_id` INT(11) NOT NULL,
    `temp_price` DECIMAL(10, 4) NOT NULL,
    `count` INT NOT NULL,
    PRIMARY KEY(`id`),
    INDEX `fk_item_order_index` (`order_id` ASC),
    CONSTRAINT `fk_item_order`
		FOREIGN KEY(`order_id`)
        REFERENCES `order` (`id`),
	CONSTRAINT `fk_item_product`
		FOREIGN KEY(`product_id`)
        REFERENCES `product` (`id`)
);

CREATE TABLE IF NOT EXISTS `review` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
    `author_id` INT(11) NOT NULL,
    `product_id` INT(11) NOT NULL,
    `rating` DECIMAL(3, 2) NOT NULL,
    `description` VARCHAR(1024) NOT NULL,
    `date` DATETIME NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_review_product_index` (`product_id` ASC),
    CONSTRAINT `fk_review_author`
		FOREIGN KEY (`author_id`)
        REFERENCES `user` (`id`),
	CONSTRAINT `fk_review_product`
		FOREIGN KEY (`product_id`)
        REFERENCES `product` (`id`)
    
);

CREATE TABLE IF NOT EXISTS `product_image` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
    `product_id` INT(11) NOT NULL,
    `image_url` VARCHAR(512) NOT NULL,
    `small_image_url` VARCHAR(512) NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_image_product_index` (`product_id` ASC),
    CONSTRAINT `fk_image_product`
		FOREIGN KEY (`product_id`)
        REFERENCES `product` (`id`)
);

CREATE TABLE IF NOT EXISTS `cart_item` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
    `user_id` INT(11) NOT NULL,
    `product_id` INT(11) NOT NULL,
    `count` INT UNSIGNED NOT NULL,
    
    PRIMARY KEY(`id`),
    CONSTRAINT `fk_cart_item_user`
		FOREIGN KEY(`user_id`)
        REFERENCES `user` (`id`),
	CONSTRAINT `fk_cart_item_product`
		FOREIGN KEY(`product_id`)
        REFERENCES `product` (`id`)
);

INSERT INTO `role`(`id`, `name`) VALUES('1', 'CUSTOMER');
INSERT INTO `role`(`id`, `name`) VALUES('2', 'ADMINISTRATOR');
