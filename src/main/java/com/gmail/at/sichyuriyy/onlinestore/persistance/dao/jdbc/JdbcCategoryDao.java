package com.gmail.at.sichyuriyy.onlinestore.persistance.dao.jdbc;

import com.gmail.at.sichyuriyy.onlinestore.domain.Category;
import com.gmail.at.sichyuriyy.onlinestore.persistance.ConnectionManager;
import com.gmail.at.sichyuriyy.onlinestore.persistance.JdbcTemplate;
import com.gmail.at.sichyuriyy.onlinestore.persistance.dao.CategoryDao;
import com.gmail.at.sichyuriyy.onlinestore.persistance.mapper.CategoryMapper;

import java.util.List;

/**
 * Created by Yuriy on 3/28/2017.
 */
public class JdbcCategoryDao implements CategoryDao {

    private static final String INSERT_CATEGORY = "INSERT INTO `category`(`title`) VALUES(?)";

    private static final String SELECT_CATEGORY_BY_ID = "SELECT * FROM `category` WHERE `id`=?";

    private static final String SELECT_ALL_CATEGORIES = "SELECT * FROM `category` ORDER BY `id` ASC";

    private static final String UPDATE_CATEGORY = "UPDATE `category` SET `title`=? WHERE `id`=?";

    private static final String DELETE_CATEGORY = "DELETE FROM `category` WHERE `id`=?";

    private ConnectionManager cm;
    private JdbcTemplate jdbcTemplate;

    public JdbcCategoryDao(ConnectionManager cm) {
        this.cm = cm;
        this.jdbcTemplate = new JdbcTemplate(cm);
    }

    @Override
    public Long create(Category category) {
        return jdbcTemplate.insert(INSERT_CATEGORY, category.getTitle());
    }

    @Override
    public Category findById(Long id) {
        return jdbcTemplate.queryObject(SELECT_CATEGORY_BY_ID,
                new CategoryMapper(), id);
    }

    @Override
    public void update(Category category) {
        jdbcTemplate.update(UPDATE_CATEGORY,
                category.getTitle(), category.getId());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(DELETE_CATEGORY, id);
    }

    @Override
    public List<Category> findAll() {
        return jdbcTemplate.queryObjects(SELECT_ALL_CATEGORIES,
                new CategoryMapper());
    }
}
