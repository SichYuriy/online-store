package com.gmail.at.sichyuriyy.onlinestore.validation.mapper;

import com.gmail.at.sichyuriyy.onlinestore.dispatcher.RequestService;
import com.gmail.at.sichyuriyy.onlinestore.entity.Category;
import com.gmail.at.sichyuriyy.onlinestore.entity.Product;
import com.gmail.at.sichyuriyy.onlinestore.validation.RequestMapper;
import org.apache.logging.log4j.LogManager;

import java.math.BigDecimal;

/**
 * Created by Yuriy on 4/24/2017.
 */
public class ProductRequestMapper implements RequestMapper<Product> {


    @Override
    public Product map(RequestService requestService) {
        Product product = new Product();
        product.setId(requestService.getLong("id"));
        product.setCategory(new Category(requestService.getLong("categoryId")));
        product.setCount(requestService.getInt("count"));
        product.setDescription(requestService.getString("description").trim());
        product.setMainImageUrl(requestService.getString("mainImageUrl").trim());

        Boolean enabled = requestService.getBool("enabled");
        LogManager.getLogger().info(enabled);
        product.setEnabled(enabled == null ? false : enabled);
        product.setPrice(new BigDecimal(requestService.getDouble("price")));
        product.setTitle(requestService.getString("title").trim());

        return product;
    }
}
