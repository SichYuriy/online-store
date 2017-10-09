INSERT INTO `category`(`id`, `title`) VALUES (1, 'category1');
INSERT INTO `category`(`id`, `title`) VALUES (2, 'category2');
INSERT INTO `category`(`id`, `title`) VALUES (3, 'category3');

INSERT INTO `user`(`id`, `login`, `password`, `black_list`)
VALUES (1, 'user1', '111111', '0');
INSERT INTO `user`(`id`, `login`, `password`, `black_list`)
VALUES (2, 'user2', '222222', '1');
INSERT INTO `user`(`id`, `login`, `password`, `black_list`)
VALUES (3, 'user3', '333333', '0');
INSERT INTO `user`(`id`, `login`, `password`, `black_list`)
VALUES (4, 'user4', '444444', '0');

INSERT INTO `user_role`(`id`, `user_id`, `role_id`)
VALUES (1, 1, 1);
INSERT INTO `user_role`(`id`, `user_id`, `role_id`)
VALUES (2, 2, 1);
INSERT INTO `user_role`(`id`, `user_id`, `role_id`)
VALUES (3, 3, 2);

INSERT INTO `product`(`id`, `category_id`, `title`, `description`, 
	`price`, `count`, `enabled`, `votes_count`, `avg_rating`, `main_image_url`)
VALUES (1, 1, 'pr1_t', 'pr1_d', 11, 1, '1', 1, 4, 'https://img1');
INSERT INTO `product`(`id`, `category_id`, `title`, `description`, 
	`price`, `count`, `enabled`, `votes_count`, `avg_rating`, `main_image_url`)
VALUES (2, 2, 'pr2_t', 'pr2_d', 22, 2, '1', 2, 4, 'https://img2');
INSERT INTO `product`(`id`, `category_id`, `title`, `description`, 
	`price`, `count`, `enabled`, `votes_count`, `avg_rating`, `main_image_url`)
VALUES (3, 2, 'pr3_t', 'pr3_d', 33, 1, '1', 1, 3, 'https://img3');
INSERT INTO `product`(`id`, `category_id`, `title`, `description`,
	`price`, `count`, `enabled`, `votes_count`, `avg_rating`, `main_image_url`)
VALUES (4, 2, 'pr4_t', 'pr4_d', 44, 1, '1', 0, 0, 'https://img4');

INSERT INTO `product_image`(`id`, `product_id`, `image_url`, `small_image_url`)
VALUES (1, 1, 'https://pr1_img1', 'https://pr1_s_img1');
INSERT INTO `product_image`(`id`, `product_id`, `image_url`, `small_image_url`)
VALUES (2, 1, 'https://pr1_img2', 'https://pr1_s_img2');
INSERT INTO `product_image`(`id`, `product_id`, `image_url`, `small_image_url`)
VALUES (3, 2, 'https://pr2_img1', 'https://pr2_s_img1');

INSERT INTO `review`(`id`, `author_id`, `product_id`, `rating`, `description`, `date`)
VALUES (1, 1, 1, 4, 'rev1', '2017-03-29 11:30:45');
INSERT INTO `review`(`id`, `author_id`, `product_id`, `rating`, `description`, `date`)
VALUES (2, 1, 2, 5, 'rev2', '2017-03-29 11:40:45');
INSERT INTO `review`(`id`, `author_id`, `product_id`, `rating`, `description`, `date`)
VALUES (3, 2, 2, 3, 'rev3', '2017-03-29 11:41:45');
INSERT INTO `review`(`id`, `author_id`, `product_id`, `rating`, `description`, `date`)
VALUES (4, 2, 3, 3, 'rev4', '2017-03-30 11:30:45');

INSERT INTO `order`(`id`, `user_id`, `date`, `status`)
VALUES (1, 1, '2017-03-29 11:30:45', 'PAID');
INSERT INTO `order`(`id`, `user_id`, `date`, `status`)
VALUES (2, 1, '2017-03-30 11:30:45', 'CANCELED');
INSERT INTO `order`(`id`, `user_id`, `date`, `status`)
VALUES (3, 2, '2017-03-29 12:30:45', 'OVERDUE');
INSERT INTO `order`(`id`, `user_id`, `date`, `status`)
VALUES (4, 2, '2017-04-29 12:30:45', 'PAID');

INSERT INTO `line_item`(`id`, `order_id`, `product_id`, `temp_price`, `count`)
VALUES (1, 1, 1, 11, 1);
INSERT INTO `line_item`(`id`, `order_id`, `product_id`, `temp_price`, `count`)
VALUES (2, 1, 2, 22, 1);
INSERT INTO `line_item`(`id`, `order_id`, `product_id`, `temp_price`, `count`)
VALUES (3, 2, 3, 33, 1);
INSERT INTO `line_item`(`id`, `order_id`, `product_id`, `temp_price`, `count`)
VALUES (4, 3, 3, 32, 1);

INSERT INTO `cart_item`(`id`, `user_id`, `product_id`, `count`)
VALUES(1, 1, 1, 1);
