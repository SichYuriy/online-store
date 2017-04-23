package com.gmail.at.sichyuriyy.onlinestore.controller;

import com.gmail.at.sichyuriyy.onlinestore.dispatcher.Controller;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.RequestService;
import com.gmail.at.sichyuriyy.onlinestore.entity.Category;
import com.gmail.at.sichyuriyy.onlinestore.entity.Product;
import com.gmail.at.sichyuriyy.onlinestore.service.CategoryService;
import com.gmail.at.sichyuriyy.onlinestore.service.ProductService;
import com.gmail.at.sichyuriyy.onlinestore.util.ServiceLocator;

import java.util.List;

/**
 * Created by Yuriy on 4/23/2017.
 */
public class ProductsController extends Controller {

    private static final int PAGE_SIZE = 3;

    private ProductService productService = ServiceLocator.INSTANCE.get(ProductService.class);
    private CategoryService categoryService = ServiceLocator.INSTANCE.get(CategoryService.class);

    @Override
    public void doGet(RequestService reqService) {
        Long categoryId = reqService.getLong("categoryId");
        Integer pageNumber = reqService.getInt("pageNum");
        if (pageNumber == null) {
            pageNumber = 1;
        }
        int limit = PAGE_SIZE;
        int offset = PAGE_SIZE * (pageNumber - 1);

        List<Product> products = productService.findByCategory(categoryId, limit, offset);
        Integer pagesCount = getPagesCount(productService.getProductsCount(categoryId));
        Category category = categoryService.findById(categoryId);

        reqService.setPageAttribute("category", category);
        reqService.setPageAttribute("pageNumber", pageNumber);
        reqService.setPageAttribute("pagesCount", pagesCount);
        reqService.setPageAttribute("products", products);

        reqService.setRenderPage("/pages/products.jsp");
    }

    private int getPagesCount(int productsCount) {
        int result = productsCount / PAGE_SIZE;
        if (productsCount % PAGE_SIZE != 0) {
            result++;
        }
        return result;
    }
}
