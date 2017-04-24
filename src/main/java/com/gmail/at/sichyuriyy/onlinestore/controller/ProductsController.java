package com.gmail.at.sichyuriyy.onlinestore.controller;

import com.gmail.at.sichyuriyy.onlinestore.dispatcher.Controller;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.RequestService;
import com.gmail.at.sichyuriyy.onlinestore.entity.Category;
import com.gmail.at.sichyuriyy.onlinestore.entity.Product;
import com.gmail.at.sichyuriyy.onlinestore.service.CategoryService;
import com.gmail.at.sichyuriyy.onlinestore.service.ProductService;
import com.gmail.at.sichyuriyy.onlinestore.util.ServiceLocator;
import com.gmail.at.sichyuriyy.onlinestore.validation.mapper.ProductRequestMapper;
import com.gmail.at.sichyuriyy.onlinestore.validation.validator.ProductValidator;

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


        List<Product> products = null;
        if (reqService.getRequest().isUserInRole("ADMINISTRATOR")) {
            products = productService.findByCategory(categoryId, limit, offset);
        } else {
            products = productService.findEnabledByCategory(categoryId, limit, offset);
        }

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


    @Override
    public void doPost(RequestService reqService) {
        Product product = new ProductRequestMapper().map(reqService);
        boolean validationStatus = new ProductValidator().getValidationStatus(product);

        if (!validationStatus) {
            reqService.putFlashParameter("product", product);
            reqService.setRedirectPath("/admin/newProduct?failed=true&categoryId=" + product.getCategory().getId());
            return;
        }
        productService.create(product);
        reqService.setRedirectPath("/products?categoryId=" + product.getCategory().getId());
    }

    @Override
    public void doPut(RequestService reqService) {
        Product product = new ProductRequestMapper().map(reqService);
        boolean validationStatus = new ProductValidator().getValidationStatus(product);

        if (!validationStatus) {
            reqService.putFlashParameter("product", product);
            reqService.setRedirectPath("/admin/editProduct?failed=true&id=" + product.getId());
            return;
        }
        productService.update(product);
        reqService.setRedirectPath("/products?categoryId=" + product.getCategory().getId());
    }

}
